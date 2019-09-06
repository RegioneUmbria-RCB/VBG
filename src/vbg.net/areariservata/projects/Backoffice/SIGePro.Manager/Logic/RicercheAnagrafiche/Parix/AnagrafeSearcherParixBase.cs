using System;
using System.IO;
using System.Net;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using System.Web;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Manager.ParixGateService;
using Init.SIGePro.Verticalizzazioni;
using Init.Utils;
using log4net;
using ImpresaRidottoNs = Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.DettaglioImpresaRidottoNs;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix
{	
    public abstract class AnagrafeSearcherParixBase : AnagrafeSearcherBase
    {
        ILog _log = LogManager.GetLogger(typeof(AnagrafeSearcherParixBase));
		ConfigurazioneParix _verticalizzazione;
		ParixProxy _parixProxy;
        

        protected AnagrafeSearcherParixBase(string className)
            : base(className)
        {
			this._verticalizzazione = new ConfigurazioneParix(x => { 
				x.IdComune = IdComune;
                x.IdComuneAlias = Alias;
				x.Database = SigeproDb;
				return x;
			});

			this._parixProxy = new ParixProxy(this._verticalizzazione);
        }

        

        private Anagrafe AdattaAnagrafica(ListaImpreseNs.RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESA result)
        {
            Anagrafe impresa = new Anagrafe();
            //Setto idcomune
            impresa.IDCOMUNE = IdComune;
            _log.Debug("IDCOMUNE " + impresa.IDCOMUNE);
            //Setto la forma giuridica
            if (result.FORMA_GIURIDICA != null)
            {
                var formaGiuridica = new FormeGiuridicheMgr(SigeproDb).GetByClass(new FormeGiuridiche{
					IDCOMUNE = IdComune,
					CODICECCIAA = result.FORMA_GIURIDICA.C_FORMA_GIURIDICA.Trim().ToUpper()
				});
				
				if (formaGiuridica != null)
                {
                    impresa.FORMAGIURIDICA = formaGiuridica.CODICEFORMAGIURIDICA;
                    _log.Debug("FORMAGIURIDICA " + impresa.FORMAGIURIDICA);
                }
            }
            //Setto CF
            impresa.CODICEFISCALE = String.IsNullOrEmpty(result.CODICE_FISCALE) ? String.Empty : result.CODICE_FISCALE.Trim().ToUpper();

            _log.Debug("CODICEFISCALE " + impresa.CODICEFISCALE);
            //Setto la PIVA
            impresa.PARTITAIVA = String.IsNullOrEmpty(result.PARTITA_IVA) ? String.Empty : result.PARTITA_IVA.Trim();

            _log.Debug("PARTITAIVA " + impresa.PARTITAIVA);
            //Setto il flag disabilitato
            ListaImpreseNs.RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA resultSede = null;
            foreach (ListaImpreseNs.RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA elem in result.DATI_ISCRIZIONE_REA)
            {
                if (!String.IsNullOrEmpty(elem.FLAG_SEDE) && elem.FLAG_SEDE.Trim().ToUpper() == "SI")
                {
                    resultSede = elem;
                    break;
                }
            }

            impresa.FLAG_DISABILITATO = "0";

            //_log.Debug("FLAG_DISABILITATO " + impresa.FLAG_DISABILITATO);

            //Setto la denominazione
            impresa.NOMINATIVO = result.DENOMINAZIONE.Trim().ToUpper();
            _log.Debug("NOMINATIVO " + impresa.NOMINATIVO);

            if (result.DATI_ISCRIZIONE_RI != null)
            {
                //Setto Nr RI
                impresa.REGDITTE = result.DATI_ISCRIZIONE_RI.NUMERO_RI.Trim().ToUpper();
                _log.Debug("REGDITTE " + impresa.REGDITTE);

                //Setto data RI
                impresa.DATAREGDITTE = String.IsNullOrEmpty(result.DATI_ISCRIZIONE_RI.DATA) ? (DateTime?)null : DateTime.ParseExact(result.DATI_ISCRIZIONE_RI.DATA.Trim(), "yyyyMMdd", null);
                _log.Debug("DATAREGDITTE " + impresa.DATAREGDITTE);
            }

            //Setto Nr REA
            impresa.NUMISCRREA = resultSede.NREA.Trim().ToUpper();
            _log.Debug("NUMISCRREA " + impresa.NUMISCRREA);

			if (resultSede != null && !String.IsNullOrEmpty(resultSede.CCIAA))
			{
				var provinciaCciaa = resultSede.CCIAA.Trim().ToUpper();
				impresa.CODCOMREGDITTE = CodiceComuneDaSiglaProvincia(provinciaCciaa);
			}

            _log.Debug("PROVINCIAREA " + impresa.PROVINCIAREA);
			//Setto provincia REA
			impresa.PROVINCIAREA = resultSede.CCIAA.Trim().ToUpper();

            //Setto la data di iscrizione REA
            impresa.DATAISCRREA = String.IsNullOrEmpty(resultSede.DATA) ? (DateTime?)null : DateTime.ParseExact(resultSede.DATA.Trim(), "yyyyMMdd", null);
            _log.Debug("DATAISCRREA " + impresa.DATAISCRREA);

            //Setto l'indirizzo
			string resultDettaglio = this._parixProxy.DettaglioRidottoImpresa(resultSede.CCIAA, resultSede.NREA);

			var dettImpresa = Deserializza<ImpresaRidottoNs.RISPOSTA>(resultDettaglio);

			//Verifico se la chiamata al ws è andata a buon fine
            if (dettImpresa.HEADER.ESITO != "OK")
				throw new Exception("La chiamata al wm DettaglioRidottoImpresa non è andata a buon fine. Codice di errore: " + ((ImpresaRidottoNs.RISPOSTADATIERRORE)dettImpresa.DATI.Item).TIPO + ".Descrizione errore: " + ((ImpresaRidottoNs.RISPOSTADATIERRORE)dettImpresa.DATI.Item).MSG_ERR);
            
			_log.DebugFormat("Tipo dell'elemento \"DATI\": {0}", dettImpresa.DATI.Item.GetType());

            if (((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE != null)
            {
				if(_log.IsDebugEnabled)
					_log.DebugFormat("Estrazione dell'indirizzo da \"dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO\": {0}", StreamUtils.SerializeClass(((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO));

                var indiriz = ((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO;

                if (indiriz != null)
                {
					var toponimo	= String.IsNullOrEmpty(indiriz.TOPONIMO) ? String.Empty : indiriz.TOPONIMO.Trim().ToUpper() + " ";
					var via			= String.IsNullOrEmpty(indiriz.VIA) ? String.Empty : indiriz.VIA.Trim().ToUpper() + " ";
					var civico		= String.IsNullOrEmpty(indiriz.N_CIVICO) ? String.Empty : indiriz.N_CIVICO.Trim().ToUpper();

					impresa.INDIRIZZO = toponimo + via + civico;
                    _log.Debug("INDIRIZZO " + impresa.INDIRIZZO);

                    //Setto la frazione
					if (!String.IsNullOrEmpty(indiriz.FRAZIONE))
					{
						impresa.CITTA = indiriz.FRAZIONE.Trim().ToUpper();
						_log.Debug("CITTA " + impresa.CITTA);
					}

                    //Setto il CAP
					if (!String.IsNullOrEmpty(indiriz.CAP))
					{
						impresa.CAP = indiriz.CAP.Trim();
						_log.Debug("CAP " + impresa.CAP);
					}

					var comuni = EstraiComuneDaNomeComuneECodiceIstat(indiriz.COMUNE, indiriz.C_COMUNE);

                    if (comuni != null)
                    {
                        impresa.COMUNERESIDENZA = comuni.CODICECOMUNE;
                        _log.Debug("COMUNERESIDENZA " + impresa.COMUNERESIDENZA);

                        impresa.PROVINCIA = comuni.SIGLAPROVINCIA;
                        _log.Debug("PROVINCIA " + impresa.PROVINCIA);
                    }

                    //Setto il fax
					if (!String.IsNullOrEmpty(indiriz.FAX))
					{
						impresa.FAX = indiriz.FAX.Trim();
						_log.Debug("FAX " + impresa.FAX);
					}

                    //Setto il telefono
					if (!String.IsNullOrEmpty(indiriz.TELEFONO))
					{
						impresa.TELEFONO = indiriz.TELEFONO.Trim();
						_log.Debug("TELEFONO " + impresa.TELEFONO);
					}

					if (!String.IsNullOrEmpty(indiriz.INDIRIZZO_PEC))
					{
						impresa.Pec = indiriz.INDIRIZZO_PEC;
						_log.Debug("INDIRIZZO_PEC " + indiriz.INDIRIZZO_PEC);
					}
                }
            }

			if (_log.IsDebugEnabled)
				_log.DebugFormat("Classe ANAGRAFE convertita: {0}", StreamUtils.SerializeClass(impresa));
            
            return impresa;
        }

		private Comuni EstraiComuneDaNomeComuneECodiceIstat(string nomeComune, string codiceIstat)
		{
			nomeComune = String.IsNullOrEmpty(nomeComune) ? String.Empty : nomeComune.Trim().ToUpper();
			codiceIstat = String.IsNullOrEmpty(codiceIstat) ? String.Empty : codiceIstat.Trim().ToUpper();

			if( String.IsNullOrEmpty(nomeComune) && String.IsNullOrEmpty( codiceIstat ) )
				return null;

			return  new ComuniMgr(SigeproDb).GetByClass( new Comuni{
				COMUNE = nomeComune,
				CODICEISTAT = codiceIstat
			} );
		}

		private string CodiceComuneDaSiglaProvincia(string siglaProvincia)
		{
			var comune = new ComuniMgr(SigeproDb).GetDatiComuneDaSiglaProvincia(siglaProvincia);

			if(comune == null)
			{
				return String.Empty;
			}

			return comune.CodiceComune;
		}


        private T Deserializza<T>(string result)
        {
			if (_log.IsDebugEnabled)
				_log.DebugFormat("AnagrafeSearcherParixBase-Deserializza<{0}>: dati da deserializzare->{1}",typeof(T).Name,result);

            try
            {
				var memStream = StreamUtils.StringToStream(result);

				var validator = new ParixXmlValidator(this._verticalizzazione.Get.Xsd);

				switch (typeof(T).Name)
                {
                    case "ListaImprese":
                        validator.ValidaListaImprese( memStream);
                        break;
                    case "DettaglioImpresaRidotto":
						validator.ValidaDettaglioImpresa(memStream);
                        break;
                }

				var serializer = new XmlSerializer(typeof(T));
				return (T)serializer.Deserialize(memStream);
            }
            catch (Exception ex)
            {
				_log.ErrorFormat("Errore durante la deserializzazione del tipo {0}: {1}", typeof(T).Name, ex.ToString() );
                throw new Exception(String.Format("Errore durante la deserializzazione del file xml restituito dal ws parix {0}", ex.Message), ex);
            }

        }



        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
			try
			{
				var anagrafica = EstraiAnagraficaDaRispostaParix(_parixProxy.RicercaImpreseNonCessatePerCodiceFiscale(partitaIva));

				if(anagrafica == null){
					return null;
				}

				if(this._verticalizzazione.Get.CercaSoloCf)
				{
					if(anagrafica.CODICEFISCALE.ToUpperInvariant() == partitaIva.ToUpperInvariant())
					{
						return anagrafica;
					}

					return null;
				}

				return anagrafica;
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante l'invocazione di parix: {0}", ex.ToString());
				throw;
			}

        }

		private Anagrafe EstraiAnagraficaDaRispostaParix(string result)
		{
			_log.DebugFormat("EstraiAnagraficaDaRispostaParix: xml={0}", result);

			var listaImprese = Deserializza<ListaImpreseNs.RISPOSTA>(result);

			if (listaImprese.HEADER.ESITO != "OK")
			{
				// Loggo l'errore restituito da parix e sollevo un'eccezione
				var rispostaErrore = (ListaImpreseNs.RISPOSTADATIERRORE)listaImprese.DATI.Item;

				_log.ErrorFormat("La chiamata al wm RicercaImpreseNonCessatePerCodiceFiscale non è andata a buon fine. Codice di errore: {0}.Descrizione errore: {1}", rispostaErrore.TIPO, rispostaErrore.MSG_ERR);

				return null;
			}

			var datiImpresa = ((ListaImpreseNs.RISPOSTADATILISTA_IMPRESE)listaImprese.DATI.Item);

			if (datiImpresa.ESTREMI_IMPRESA == null || datiImpresa.ESTREMI_IMPRESA.Length == 0)
				return null;

			return AdattaAnagrafica(datiImpresa.ESTREMI_IMPRESA[0]);
		}
    }
}
