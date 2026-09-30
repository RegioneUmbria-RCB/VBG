using Init.SIGePro.Data;
using Init.Utils;
using log4net;
using System;
using System.Linq;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier
{
    public abstract class AnagrafeSearcherAdrierBase : AnagrafeSearcherBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(AnagrafeSearcherAdrierBase));
        private readonly ConfigurazioneAdrier _verticalizzazione;
        private readonly AdrierProxy _adrierProxy;

        public AnagrafeSearcherAdrierBase(string className) : base(className)
        {
            this._verticalizzazione = new ConfigurazioneAdrier(x =>
            {
                x.IdComune = this.IdComune;
                x.IdComuneAlias = this.Alias;
                x.Database = this.SigeproDb;
                return x;
            });

            this._adrierProxy = new AdrierProxy(this._verticalizzazione);
        }

        private Anagrafe AdattaAnagrafica(RispostaListaImprese.EstremiImpresaLista result)
        {
            Anagrafe impresa = new Anagrafe();
            impresa.IDCOMUNE = this.IdComune;

            if (result.FormaGiuridica != null)
            {
                var formaGiuridica = new FormeGiuridicheMgr(this.SigeproDb).GetByClass(new FormeGiuridiche
                {
                    IDCOMUNE = this.IdComune,
                    CODICECCIAA = result.FormaGiuridica.Codice.Trim().ToUpper()
                });

                if (formaGiuridica != null)
                {
                    impresa.FORMAGIURIDICA = formaGiuridica.CODICEFORMAGIURIDICA;
                    this._log.Debug("FORMAGIURIDICA " + impresa.FORMAGIURIDICA);
                }
            }
            //Setto CF
            impresa.CODICEFISCALE = String.IsNullOrEmpty(result.CodiceFiscale) ? String.Empty : result.CodiceFiscale.Trim().ToUpper();

            this._log.Debug("CODICEFISCALE " + impresa.CODICEFISCALE);
            //Setto la PIVA
            impresa.PARTITAIVA = String.IsNullOrEmpty(result.PartitaIva) ? String.Empty : result.PartitaIva.Trim();

            this._log.Debug("PARTITAIVA " + impresa.PARTITAIVA);

            ////Setto il flag disabilitato
            //Risposta.DatiIscrizioneRea resultSede = null;

            //if (!String.IsNullOrEmpty(result.IscrizioneRea.FlagSede) && result.IscrizioneRea.FlagSede.Trim().ToUpper() == "SI")
            //{
            //    resultSede = result.IscrizioneRea;
            //}

            impresa.FLAG_DISABILITATO = "0";

            //Setto la denominazione
            impresa.NOMINATIVO = result.Denominazione.Trim().ToUpper();
            this._log.Debug("NOMINATIVO " + impresa.NOMINATIVO);

            if (result.IscrizioneRi != null)
            {
                //Setto data RI
                impresa.DATAREGDITTE = String.IsNullOrEmpty(result.IscrizioneRi.Data.Trim()) ? (DateTime?)null : DateTime.ParseExact(result.IscrizioneRi.Data.Trim(), "yyyyMMdd", null);
                this._log.Debug("DATAREGDITTE " + impresa.DATAREGDITTE);
            }

            //Setto Nr REA
            impresa.NUMISCRREA = result.IscrizioneRea.NRea.Trim().ToUpper();
            this._log.Debug("NUMISCRREA " + impresa.NUMISCRREA);

            if (result.IscrizioneRea != null && !String.IsNullOrEmpty(result.IscrizioneRea.Cciaa))
            {
                var comune = new ComuniMgr(this.SigeproDb).GetDatiComuneDaSiglaProvincia(result.IscrizioneRea.Cciaa.Trim().ToUpper());
                impresa.CODCOMREGDITTE = comune == null ? "" : comune.CodiceComune;
            }

            //Setto provincia REA
            impresa.PROVINCIAREA = result.IscrizioneRea.Cciaa.Trim().ToUpper();
            this._log.Debug("PROVINCIAREA " + impresa.PROVINCIAREA);

            //Setto la data di iscrizione REA
            impresa.DATAISCRREA = String.IsNullOrEmpty(result.IscrizioneRea.Data) ? (DateTime?)null : DateTime.ParseExact(result.IscrizioneRea.Data.Trim(), "yyyyMMdd", null);
            this._log.Debug("DATAISCRREA " + impresa.DATAISCRREA);

            //Setto l'indirizzo
            var resultDettaglio = this._adrierProxy.DettaglioRidottoImpresa(result.IscrizioneRea.Cciaa, result.IscrizioneRea.NRea);

            var dettImpresa = this.Deserializza<RispostaDettaglioImpresa>(resultDettaglio);

            //Verifico se la chiamata al ws è andata a buon fine
            if (dettImpresa.Header.Esito != "OK")
            {
                throw new Exception($"La chiamata al wm DettaglioRidottoImpresa non è andata a buon fine. Codice di errore: {((RispostaDettaglioImpresa.ErroreDatiImpresa)dettImpresa.DatiDettaglioImpresa.Item).Tipo}. Descrizione errore: {((RispostaDettaglioImpresa.ErroreDatiImpresa)dettImpresa.DatiDettaglioImpresa.Item).MessaggioErrore}");
            }

            this._log.DebugFormat("Tipo dell'elemento \"DATI\": {0}", dettImpresa.DatiDettaglioImpresa.Item.GetType());

            if (((RispostaDettaglioImpresa.DatiImpresaDettaglio)dettImpresa.DatiDettaglioImpresa.Item).InformazioniSede != null)
            {
                var estremiImprese = ((RispostaDettaglioImpresa.DatiImpresaDettaglio)dettImpresa.DatiDettaglioImpresa.Item).EstremiImpresa;

                if (estremiImprese != null && estremiImprese.Length > 0)
                {
                    var datiImpresa = estremiImprese[0];
                    if (!String.IsNullOrEmpty(datiImpresa.PartitaIva))
                    {
                        impresa.PARTITAIVA = datiImpresa.PartitaIva.Trim();
                        this._log.DebugFormat("AGGIORNATO DATO PARTITA IVA DA DETTAGLIO IMPRESA: {0}", datiImpresa.PartitaIva.Trim());
                    }

                    if (!String.IsNullOrEmpty(datiImpresa.CodiceFiscale))
                    {
                        impresa.CODICEFISCALE = datiImpresa.CodiceFiscale.Trim();
                        this._log.DebugFormat("AGGIORNATO DATO CODICE FISCALE DA DETTAGLIO IMPRESA: {0}", datiImpresa.CodiceFiscale.Trim());
                    }

                    if (!String.IsNullOrEmpty(datiImpresa.Denominazione))
                    {
                        impresa.NOMINATIVO = datiImpresa.Denominazione.Trim().ToUpper();
                        this._log.DebugFormat("AGGIORNATO DATO DENOMINAZIONE DA DETTAGLIO IMPRESA: {0}", datiImpresa.Denominazione.Trim());
                    }
                }

                if (this._log.IsDebugEnabled)
                {
                    this._log.DebugFormat("Estrazione dell'indirizzo da \"dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO\": {0}", StreamUtils.SerializeClass(((RispostaDettaglioImpresa.DatiImpresaDettaglio)dettImpresa.DatiDettaglioImpresa.Item).InformazioniSede));
                }
                var indiriz = ((RispostaDettaglioImpresa.DatiImpresaDettaglio)dettImpresa.DatiDettaglioImpresa.Item).InformazioniSede.Indirizzo;

                if (indiriz != null)
                {
                    var toponimo = String.IsNullOrEmpty(indiriz.Toponimo) ? String.Empty : indiriz.Toponimo.Trim().ToUpper() + " ";
                    var via = String.IsNullOrEmpty(indiriz.Via) ? String.Empty : indiriz.Via.Trim().ToUpper() + " ";
                    var civico = String.IsNullOrEmpty(indiriz.NumeroCivico) ? String.Empty : indiriz.NumeroCivico.Trim().ToUpper();

                    impresa.INDIRIZZO = toponimo + via + civico;
                    this._log.Debug("INDIRIZZO " + impresa.INDIRIZZO);

                    //Setto il CAP
                    if (!String.IsNullOrEmpty(indiriz.Cap))
                    {
                        impresa.CAP = indiriz.Cap.Trim();
                        this._log.Debug("CAP " + impresa.CAP);
                    }

                    var comuni = this.EstraiComuneDaNomeComuneECodiceIstat(indiriz.Comune, indiriz.CodiceComune);

                    if (comuni != null)
                    {
                        impresa.COMUNERESIDENZA = comuni.CODICECOMUNE;
                        this._log.Debug("COMUNERESIDENZA " + impresa.COMUNERESIDENZA);

                        impresa.PROVINCIA = comuni.SIGLAPROVINCIA;
                        this._log.Debug("PROVINCIA " + impresa.PROVINCIA);
                    }

                    if (!String.IsNullOrEmpty(indiriz.IndirizzoPec))
                    {
                        impresa.Pec = indiriz.IndirizzoPec;
                        this._log.Debug("INDIRIZZO_PEC " + indiriz.IndirizzoPec);
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

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            try
            {
                var anagrafica = this.EstraiAnagraficaDaRispostaAdrier(this._adrierProxy.RicercaImpreseNonCessatePerPartitaIva(partitaIva));

                if (anagrafica == null)
                {
                    return null;
                }

                if (this._verticalizzazione.Get.CercaSoloCf == "1")
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
                this._log.ErrorFormat("Errore durante l'invocazione di Adrier: {0}", ex.ToString());
                throw;
            }
        }

        private T Deserializza<T>(string result)
        {
            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("AnagrafeSearcherAdrierBase-Deserializza<{0}>: dati da deserializzare->{1}", typeof(T).Name, result);

            try
            {
                var memStream = StreamUtils.StringToStream(result);

                XmlSerializer serializer = new XmlSerializer(typeof(T));
                return (T)serializer.Deserialize(memStream);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la deserializzazione del tipo {0}: {1}", typeof(T).Name, ex.ToString());
                throw new Exception(String.Format("Errore durante la deserializzazione del file xml restituito dal ws adrier {0}", ex.Message), ex);
            }

        }

        private Anagrafe EstraiAnagraficaDaRispostaAdrier(string result)
        {
            this._log.DebugFormat("EstraiAnagraficaDaRispostaAdrier: xml={0}", result);



            var listaImprese = this.Deserializza<RispostaListaImprese>(result);

            if (listaImprese.Header.Esito != "OK")
            {
                // Loggo l'errore restituito da adrier e sollevo un'eccezione
                RispostaListaImprese.Errore rispostaErrore = (RispostaListaImprese.Errore)listaImprese.DatiListaImpresa.Item;

                this._log.ErrorFormat("La chiamata al wm RicercaImpreseNonCessatePerCodiceFiscale non è andata a buon fine. Codice di errore: {0}.Descrizione errore: {1}", rispostaErrore.Tipo, rispostaErrore.MessaggioErrore);

                return null;
            }

            RispostaListaImprese.ListaImprese datiImpresa = (RispostaListaImprese.ListaImprese)listaImprese.DatiListaImpresa.Item;

            if (datiImpresa.EstremiImpresa == null || datiImpresa.EstremiImpresa.Length == 0)
            {
                return null;
            }

            var estremiImpresa = datiImpresa.EstremiImpresa.Where(x => x.IscrizioneRea != null && !String.IsNullOrEmpty(x.IscrizioneRea.NRea));

            if (estremiImpresa == null || estremiImpresa.Count() == 0)
            {
                return null;
            }

            return this.AdattaAnagrafica(estremiImpresa.ToArray()[0]);
        }
    }
}
