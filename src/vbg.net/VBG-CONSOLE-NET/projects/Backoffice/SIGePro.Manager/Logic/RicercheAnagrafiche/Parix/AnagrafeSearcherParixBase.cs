using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.ListaImpreseNs;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using System;
using System.IO;
using System.Xml.Serialization;
using ImpresaRidottoNs = Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.DettaglioImpresaRidottoNs;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix
{
    public abstract class AnagrafeSearcherParixBase : AnagrafeSearcherBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AnagrafeSearcherParixBase));
        private readonly ConfigurazioneParix _verticalizzazione;
        private readonly ParixProxy _parixProxy;


        protected AnagrafeSearcherParixBase(string className)
            : base(className)
        {
            this._verticalizzazione = new ConfigurazioneParix(x =>
            {
                x.IdComune = this.IdComune;
                x.IdComuneAlias = this.Alias;
                x.Database = this.SigeproDb;
                return x;
            });

            this._parixProxy = new ParixProxy(this._verticalizzazione);
        }

        public override void InitParams(string idComune, string alias, DataBase db)
        {
            base.InitParams(idComune, alias, db);
        }



        private Anagrafe AdattaAnagrafica(ListaImpreseNs.RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESA result)
        {
            Anagrafe impresa = new Anagrafe();
            //Setto idcomune
            impresa.IDCOMUNE = this.IdComune;
            this._log.Debug("IDCOMUNE " + impresa.IDCOMUNE);
            //Setto la forma giuridica
            if (result.FORMA_GIURIDICA != null)
            {
                FormeGiuridiche formaGiuridica = new FormeGiuridicheMgr(this.SigeproDb).GetByClass(new FormeGiuridiche
                {
                    IDCOMUNE = this.IdComune,
                    CODICECCIAA = result.FORMA_GIURIDICA.C_FORMA_GIURIDICA.Trim().ToUpper()
                });

                if (formaGiuridica != null)
                {
                    impresa.FORMAGIURIDICA = formaGiuridica.CODICEFORMAGIURIDICA;
                    this._log.Debug("FORMAGIURIDICA " + impresa.FORMAGIURIDICA);
                }
            }
            //Setto CF
            impresa.CODICEFISCALE = String.IsNullOrEmpty(result.CODICE_FISCALE) ? String.Empty : result.CODICE_FISCALE.Trim().ToUpper();

            this._log.Debug("CODICEFISCALE " + impresa.CODICEFISCALE);
            //Setto la PIVA
            impresa.PARTITAIVA = String.IsNullOrEmpty(result.PARTITA_IVA) ? String.Empty : result.PARTITA_IVA.Trim();

            this._log.Debug("PARTITAIVA " + impresa.PARTITAIVA);
            //Setto il flag disabilitato
            ListaImpreseNs.RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA resultSede = null;
            foreach (RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA elem in result.DATI_ISCRIZIONE_REA)
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
            this._log.Debug("NOMINATIVO " + impresa.NOMINATIVO);

            if (result.DATI_ISCRIZIONE_RI != null)
            {
                //Setto Nr RI
                impresa.REGDITTE = result.DATI_ISCRIZIONE_RI.NUMERO_RI.Trim().ToUpper();
                this._log.Debug("REGDITTE " + impresa.REGDITTE);

                //Setto data RI
                impresa.DATAREGDITTE = String.IsNullOrEmpty(result.DATI_ISCRIZIONE_RI.DATA) ? (DateTime?)null : DateTime.ParseExact(result.DATI_ISCRIZIONE_RI.DATA.Trim(), "yyyyMMdd", null);
                this._log.Debug("DATAREGDITTE " + impresa.DATAREGDITTE);
            }

            //Setto Nr REA
            impresa.NUMISCRREA = resultSede.NREA.Trim().ToUpper();
            this._log.Debug("NUMISCRREA " + impresa.NUMISCRREA);

            if (resultSede != null && !String.IsNullOrEmpty(resultSede.CCIAA))
            {
                var provinciaCciaa = resultSede.CCIAA.Trim().ToUpper();
                impresa.CODCOMREGDITTE = this.CodiceComuneDaSiglaProvincia(provinciaCciaa);
            }

            this._log.Debug("PROVINCIAREA " + impresa.PROVINCIAREA);
            //Setto provincia REA
            impresa.PROVINCIAREA = resultSede.CCIAA.Trim().ToUpper();

            //Setto la data di iscrizione REA
            impresa.DATAISCRREA = String.IsNullOrEmpty(resultSede.DATA) ? (DateTime?)null : DateTime.ParseExact(resultSede.DATA.Trim(), "yyyyMMdd", null);
            this._log.Debug("DATAISCRREA " + impresa.DATAISCRREA);

            //Setto l'indirizzo
            string resultDettaglio = this._parixProxy.DettaglioRidottoImpresa(resultSede.CCIAA, resultSede.NREA);

            ImpresaRidottoNs.RISPOSTA dettImpresa = this.Deserializza<ImpresaRidottoNs.RISPOSTA>(resultDettaglio);

            //Verifico se la chiamata al ws è andata a buon fine
            if (dettImpresa.HEADER.ESITO != "OK")
                throw new Exception("La chiamata al wm DettaglioRidottoImpresa non è andata a buon fine. Codice di errore: " + ((ImpresaRidottoNs.RISPOSTADATIERRORE)dettImpresa.DATI.Item).TIPO + ".Descrizione errore: " + ((ImpresaRidottoNs.RISPOSTADATIERRORE)dettImpresa.DATI.Item).MSG_ERR);

            this._log.DebugFormat("Tipo dell'elemento \"DATI\": {0}", dettImpresa.DATI.Item.GetType());

            if (((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE != null)
            {
                if (this._log.IsDebugEnabled)
                    this._log.DebugFormat("Estrazione dell'indirizzo da \"dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO\": {0}", StreamUtils.SerializeClass(((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO));

                ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO indiriz = ((ImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO;

                if (indiriz != null)
                {
                    var toponimo = String.IsNullOrEmpty(indiriz.TOPONIMO) ? String.Empty : indiriz.TOPONIMO.Trim().ToUpper() + " ";
                    var via = String.IsNullOrEmpty(indiriz.VIA) ? String.Empty : indiriz.VIA.Trim().ToUpper() + " ";
                    var civico = String.IsNullOrEmpty(indiriz.N_CIVICO) ? String.Empty : indiriz.N_CIVICO.Trim().ToUpper();

                    impresa.INDIRIZZO = toponimo + via + civico;
                    this._log.Debug("INDIRIZZO " + impresa.INDIRIZZO);

                    //Setto la frazione
                    if (!String.IsNullOrEmpty(indiriz.FRAZIONE))
                    {
                        impresa.CITTA = indiriz.FRAZIONE.Trim().ToUpper();
                        this._log.Debug("CITTA " + impresa.CITTA);
                    }

                    //Setto il CAP
                    if (!String.IsNullOrEmpty(indiriz.CAP))
                    {
                        impresa.CAP = indiriz.CAP.Trim();
                        this._log.Debug("CAP " + impresa.CAP);
                    }

                    Comuni comuni = this.EstraiComuneDaNomeComuneECodiceIstat(indiriz.COMUNE, indiriz.C_COMUNE);

                    if (comuni != null)
                    {
                        impresa.COMUNERESIDENZA = comuni.CODICECOMUNE;
                        this._log.Debug("COMUNERESIDENZA " + impresa.COMUNERESIDENZA);

                        impresa.PROVINCIA = comuni.SIGLAPROVINCIA;
                        this._log.Debug("PROVINCIA " + impresa.PROVINCIA);
                    }

                    //Setto il fax
                    if (!String.IsNullOrEmpty(indiriz.FAX))
                    {
                        impresa.FAX = indiriz.FAX.Trim();
                        this._log.Debug("FAX " + impresa.FAX);
                    }

                    //Setto il telefono
                    if (!String.IsNullOrEmpty(indiriz.TELEFONO))
                    {
                        impresa.TELEFONO = indiriz.TELEFONO.Trim();
                        this._log.Debug("TELEFONO " + impresa.TELEFONO);
                    }

                    if (!String.IsNullOrEmpty(indiriz.INDIRIZZO_PEC))
                    {
                        impresa.Pec = indiriz.INDIRIZZO_PEC;
                        this._log.Debug("INDIRIZZO_PEC " + indiriz.INDIRIZZO_PEC);
                    }
                }
            }

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Classe ANAGRAFE convertita: {0}", StreamUtils.SerializeClass(impresa));

            return impresa;
        }

        private Comuni EstraiComuneDaNomeComuneECodiceIstat(string nomeComune, string codiceIstat)
        {
            nomeComune = String.IsNullOrEmpty(nomeComune) ? String.Empty : nomeComune.Trim().ToUpper();
            codiceIstat = String.IsNullOrEmpty(codiceIstat) ? String.Empty : codiceIstat.Trim().ToUpper();

            if (String.IsNullOrEmpty(nomeComune) && String.IsNullOrEmpty(codiceIstat))
                return null;

            return new ComuniMgr(this.SigeproDb).GetByClass(new Comuni
            {
                COMUNE = nomeComune,
                CODICEISTAT = codiceIstat
            });
        }

        private string CodiceComuneDaSiglaProvincia(string siglaProvincia)
        {
            ComuniMgr.DatiComuneCompatto comune = new ComuniMgr(this.SigeproDb).GetDatiComuneDaSiglaProvincia(siglaProvincia);

            if (comune == null)
            {
                return String.Empty;
            }

            return comune.CodiceComune;
        }


        private T Deserializza<T>(string result)
        {
            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("AnagrafeSearcherParixBase-Deserializza<{0}>: dati da deserializzare->{1}", typeof(T).Name, result);

            try
            {
                MemoryStream memStream = StreamUtils.StringToStream(result);

                ParixXmlValidator validator = new ParixXmlValidator(this._verticalizzazione.Xsd);

                switch (typeof(T).Name)
                {
                    case "ListaImprese":
                        validator.ValidaListaImprese(memStream);
                        break;
                    case "DettaglioImpresaRidotto":
                        validator.ValidaDettaglioImpresa(memStream);
                        break;
                }

                XmlSerializer serializer = new XmlSerializer(typeof(T));
                return (T)serializer.Deserialize(memStream);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la deserializzazione del tipo {0}: {1}", typeof(T).Name, ex.ToString());
                throw new Exception(String.Format("Errore durante la deserializzazione del file xml restituito dal ws parix {0}", ex.Message), ex);
            }

        }



        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            try
            {
                Anagrafe anagrafica = this.EstraiAnagraficaDaRispostaParix(this._parixProxy.RicercaImpreseNonCessatePerCodiceFiscale(partitaIva));

                if (anagrafica == null)
                {
                    return null;
                }

                if (this._verticalizzazione.CercaSoloCf)
                {
                    if (anagrafica.CODICEFISCALE.ToUpperInvariant() == partitaIva.ToUpperInvariant())
                    {
                        return anagrafica;
                    }

                    return null;
                }

                return anagrafica;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'invocazione di parix: {0}", ex.ToString());
                throw;
            }

        }

        private Anagrafe EstraiAnagraficaDaRispostaParix(string result)
        {
            this._log.DebugFormat("EstraiAnagraficaDaRispostaParix: xml={0}", result);

            RISPOSTA listaImprese = this.Deserializza<ListaImpreseNs.RISPOSTA>(result);

            if (listaImprese.HEADER.ESITO != "OK")
            {
                // Loggo l'errore restituito da parix e sollevo un'eccezione
                RISPOSTADATIERRORE rispostaErrore = (ListaImpreseNs.RISPOSTADATIERRORE)listaImprese.DATI.Item;

                this._log.ErrorFormat("La chiamata al wm RicercaImpreseNonCessatePerCodiceFiscale non è andata a buon fine. Codice di errore: {0}.Descrizione errore: {1}", rispostaErrore.TIPO, rispostaErrore.MSG_ERR);

                return null;
            }

            RISPOSTADATILISTA_IMPRESE datiImpresa = ((ListaImpreseNs.RISPOSTADATILISTA_IMPRESE)listaImprese.DATI.Item);

            if (datiImpresa.ESTREMI_IMPRESA == null || datiImpresa.ESTREMI_IMPRESA.Length == 0)
                return null;

            return this.AdattaAnagrafica(datiImpresa.ESTREMI_IMPRESA[0]);
        }
    }
}
