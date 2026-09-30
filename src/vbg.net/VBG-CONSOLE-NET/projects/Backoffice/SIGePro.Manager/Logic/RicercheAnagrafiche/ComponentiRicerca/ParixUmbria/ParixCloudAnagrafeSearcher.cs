using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Exceptions;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.DettaglioImpresaRidottoNs;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix.ListaImpreseNs;
using Init.Utils;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.ParixUmbria
{
    public class ParixCloudAnagrafeSearcher : IAnagrafeSearcher
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ParixCloudAnagrafeSearcher));
        private VerticalizzazioneWsAnagrafeParixCloud _verticalizzazione;
        private ParixCloudProxy _parixProxy;
        private readonly Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher _searcherSigepro;

        private string _idComune = "";
        private DataBase _sigeproDb;

        public Dictionary<string, string> Configuration => new Dictionary<string, string>();

        internal ParixCloudAnagrafeSearcher()
        {

            this._searcherSigepro = new AnagrafeSearcher("PARIXSTANDARD");
        }

        public void InitParams(string idComune, string alias, DataBase db)
        {
            this._idComune = idComune;
            this._sigeproDb = db;

            this._searcherSigepro.InitParams(idComune, alias, db);
            this._verticalizzazione = new VerticalizzazioneWsAnagrafeParixCloud(alias, "TT");
            this._parixProxy = new ParixCloudProxy(this._verticalizzazione);
        }



        private Anagrafe AdattaAnagrafica(RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESA datiImpresa)
        {
            Anagrafe anagrafe = new Anagrafe();
            //Setto idcomune
            anagrafe.IDCOMUNE = this._idComune;
            anagrafe.FLAG_DISABILITATO = "0";

            this._log.DebugFormat("IDCOMUNE {0}", anagrafe.IDCOMUNE);

            //Setto la forma giuridica
            if (!String.IsNullOrEmpty(datiImpresa.FORMA_GIURIDICA?.C_FORMA_GIURIDICA))
            {
                string codiceCciaa = datiImpresa.FORMA_GIURIDICA.C_FORMA_GIURIDICA.Trim().ToUpper();

                FormeGiuridiche formaGiuridica = new FormeGiuridicheMgr(this._sigeproDb).GetByCodiceCciaa(this._idComune, codiceCciaa);

                if (formaGiuridica != null)
                {
                    anagrafe.FORMAGIURIDICA = formaGiuridica.CODICEFORMAGIURIDICA;
                    this._log.DebugFormat("FORMAGIURIDICA {0}", anagrafe.FORMAGIURIDICA);
                }
            }
            //Setto CF
            anagrafe.CODICEFISCALE = String.IsNullOrEmpty(datiImpresa.CODICE_FISCALE) ? String.Empty : datiImpresa.CODICE_FISCALE.Trim().ToUpper();

            this._log.DebugFormat("CODICEFISCALE {0}", anagrafe.CODICEFISCALE);
            //Setto la PIVA
            anagrafe.PARTITAIVA = String.IsNullOrEmpty(datiImpresa.PARTITA_IVA) ? String.Empty : datiImpresa.PARTITA_IVA.Trim();

            this._log.DebugFormat("PARTITAIVA {0}", anagrafe.PARTITAIVA);
            //Setto il flag disabilitato
            RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA sedePrincipale = datiImpresa.DATI_ISCRIZIONE_REA.FirstOrDefault(elem => elem.FLAG_SEDE?.Trim().ToUpper() == "SI");

            //Setto la denominazione
            anagrafe.NOMINATIVO = datiImpresa.DENOMINAZIONE.Trim().ToUpper();
            this._log.DebugFormat("NOMINATIVO {0}", anagrafe.NOMINATIVO);

            if (datiImpresa.DATI_ISCRIZIONE_RI != null)
            {
                //Setto Nr RI
                anagrafe.REGDITTE = datiImpresa.DATI_ISCRIZIONE_RI.NUMERO_RI.Trim().ToUpper();
                //Setto data RI
                anagrafe.DATAREGDITTE = String.IsNullOrEmpty(datiImpresa.DATI_ISCRIZIONE_RI?.DATA) ? (DateTime?)null : DateTime.ParseExact(datiImpresa.DATI_ISCRIZIONE_RI.DATA.Trim(), "yyyyMMdd", null);

                this._log.DebugFormat("REGDITTE {0}, DATAREGDITTE {1}", anagrafe.REGDITTE, anagrafe.DATAREGDITTE);
            }

            //Setto Nr REA
            anagrafe.NUMISCRREA = sedePrincipale?.NREA.Trim().ToUpper() ?? "";
            this._log.DebugFormat("NUMISCRREA {0}", anagrafe.NUMISCRREA);

            if (!String.IsNullOrEmpty(sedePrincipale?.CCIAA))
            {
                string provinciaCciaa = sedePrincipale.CCIAA.Trim().ToUpper();
                anagrafe.CODCOMREGDITTE = this.CodiceComuneDaSiglaProvincia(provinciaCciaa); // ??? per una sigla provincia potrebbe restituire svariati comuni!
            }

            this._log.DebugFormat("PROVINCIAREA {0}", anagrafe.PROVINCIAREA);
            //Setto provincia REA
            anagrafe.PROVINCIAREA = sedePrincipale?.CCIAA.Trim().ToUpper() ?? "";

            //Setto la data di iscrizione REA
            anagrafe.DATAISCRREA = String.IsNullOrEmpty(sedePrincipale?.DATA) ? (DateTime?)null : DateTime.ParseExact(sedePrincipale.DATA.Trim(), "yyyyMMdd", null);
            this._log.DebugFormat("DATAISCRREA {0}", anagrafe.DATAISCRREA);

            //Setto l'indirizzo
            this.CaricaDatiIndirizzo(anagrafe, sedePrincipale);

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Classe ANAGRAFE convertita: {0}", StreamUtils.SerializeClass(anagrafe));

            return anagrafe;
        }

        private void CaricaDatiIndirizzo(Anagrafe impresa, RISPOSTADATILISTA_IMPRESEESTREMI_IMPRESADATI_ISCRIZIONE_REA resultSede)
        {
            string resultDettaglio = this._parixProxy.DettaglioRidottoImpresa(resultSede.CCIAA, resultSede.NREA);

            Parix.DettaglioImpresaRidottoNs.RISPOSTA dettImpresa = this.Deserializza<Parix.DettaglioImpresaRidottoNs.RISPOSTA>(resultDettaglio);

            if (dettImpresa == null)
                throw new RicercaAnagraficaException("La deserializzazione della risposta del ws DettaglioRidottoImpresa non è andata a buon fine.");

            //Verifico se la chiamata al ws è andata a buon fine
            if (dettImpresa.HEADER.ESITO != "OK")
            {
                Parix.DettaglioImpresaRidottoNs.RISPOSTADATIERRORE err = ((Parix.DettaglioImpresaRidottoNs.RISPOSTADATIERRORE)dettImpresa.DATI.Item);
                throw new RicercaAnagraficaException("La chiamata al wm DettaglioRidottoImpresa non è andata a buon fine. Codice di errore: " + err.TIPO + ".Descrizione errore: " + err.MSG_ERR);
            }

            this.AdattaInformazioniIndirizzoSede(impresa, dettImpresa);
        }

        private void AdattaInformazioniIndirizzoSede(Anagrafe impresa, Parix.DettaglioImpresaRidottoNs.RISPOSTA dettImpresa)
        {
            this._log.DebugFormat("Tipo dell'elemento \"DATI\": {0}", dettImpresa.DATI.Item.GetType());
            RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDE informazioniSede = ((Parix.DettaglioImpresaRidottoNs.RISPOSTADATIDATI_IMPRESA)dettImpresa.DATI.Item).INFORMAZIONI_SEDE;

            if (informazioniSede == null)
            {
                return;
            }

            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("Estrazione dell'indirizzo da \"dettImpresa.DATI.Item).INFORMAZIONI_SEDE.INDIRIZZO\": {0}", StreamUtils.SerializeClass(informazioniSede.INDIRIZZO));

            RISPOSTADATIDATI_IMPRESAINFORMAZIONI_SEDEINDIRIZZO indiriz = informazioniSede.INDIRIZZO;

            if (indiriz == null)
            {
                return;
            }

            string toponimo = String.IsNullOrEmpty(indiriz.TOPONIMO) ? String.Empty : indiriz.TOPONIMO.Trim().ToUpper() + " ";
            string via = String.IsNullOrEmpty(indiriz.VIA) ? String.Empty : indiriz.VIA.Trim().ToUpper() + " ";
            string civico = String.IsNullOrEmpty(indiriz.N_CIVICO) ? String.Empty : indiriz.N_CIVICO.Trim().ToUpper();

            impresa.INDIRIZZO = toponimo + via + civico;
            this._log.DebugFormat("INDIRIZZO {0}", impresa.INDIRIZZO);

            //Setto la frazione
            if (!String.IsNullOrEmpty(indiriz.FRAZIONE))
            {
                impresa.CITTA = indiriz.FRAZIONE.Trim().ToUpper();
                this._log.DebugFormat("CITTA {0}", impresa.CITTA);
            }

            //Setto il CAP
            if (!String.IsNullOrEmpty(indiriz.CAP))
            {
                impresa.CAP = indiriz.CAP.Trim();
                this._log.DebugFormat("CAP {0}", impresa.CAP);
            }

            Comuni comuni = this.EstraiComuneDaNomeComuneECodiceIstat(indiriz.COMUNE, indiriz.C_COMUNE);

            if (comuni != null)
            {
                impresa.COMUNERESIDENZA = comuni.CODICECOMUNE;
                this._log.DebugFormat("COMUNERESIDENZA {0}", impresa.COMUNERESIDENZA);

                impresa.PROVINCIA = comuni.SIGLAPROVINCIA;
                this._log.DebugFormat("PROVINCIA {0}", impresa.PROVINCIA);
            }

            //Setto il fax
            if (!String.IsNullOrEmpty(indiriz.FAX))
            {
                impresa.FAX = indiriz.FAX.Trim();
                this._log.DebugFormat("FAX {0}", impresa.FAX);
            }

            //Setto il telefono
            if (!String.IsNullOrEmpty(indiriz.TELEFONO))
            {
                impresa.TELEFONO = indiriz.TELEFONO.Trim();
                this._log.DebugFormat("TELEFONO {0}", impresa.TELEFONO);
            }

            if (!String.IsNullOrEmpty(indiriz.INDIRIZZO_PEC))
            {
                impresa.Pec = indiriz.INDIRIZZO_PEC;
                this._log.DebugFormat("INDIRIZZO_PEC {0}", indiriz.INDIRIZZO_PEC);
            }
        }

        private Comuni EstraiComuneDaNomeComuneECodiceIstat(string nomeComune, string codiceIstat)
        {
            nomeComune = String.IsNullOrEmpty(nomeComune) ? String.Empty : nomeComune.Trim().ToUpper();
            codiceIstat = String.IsNullOrEmpty(codiceIstat) ? String.Empty : codiceIstat.Trim().ToUpper();

            if (String.IsNullOrEmpty(nomeComune) && String.IsNullOrEmpty(codiceIstat))
                return null;

            return new ComuniMgr(this._sigeproDb).GetByClass(new Comuni
            {
                COMUNE = nomeComune,
                CODICEISTAT = codiceIstat
            });
        }

        private string CodiceComuneDaSiglaProvincia(string siglaProvincia)
        {
            ComuniMgr.DatiComuneCompatto comune = new ComuniMgr(this._sigeproDb).GetDatiComuneDaSiglaProvincia(siglaProvincia);

            if (comune == null)
            {
                return String.Empty;
            }

            return comune.CodiceComune;
        }


        private T Deserializza<T>(string result)
        {
            if (this._log.IsDebugEnabled)
                this._log.DebugFormat("{0}-Deserializza<{1}>: dati da deserializzare->{2}", nameof(ParixCloudAnagrafeSearcher), typeof(T).Name, result);

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
                this._log.ErrorFormat("ParixCloudAnagrafeSearcher - Errore durante la deserializzazione del tipo {0}: {1}", typeof(T).Name, ex.ToString());
                throw new RicercaAnagraficaException(String.Format("Errore durante la deserializzazione del file xml restituito dal ws parix {0}", ex.Message), ex);
            }

        }



        public Anagrafe ByPartitaIvaImp(string partitaIva)
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
                    if (anagrafica.CODICEFISCALE.Equals(partitaIva, StringComparison.InvariantCultureIgnoreCase))
                    {
                        return anagrafica;
                    }

                    return null;
                }

                return anagrafica;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("ParixCloudAnagrafeSearcher - Errore durante l'invocazione di parix: {0}", ex.ToString());
                throw;
            }
        }

        private Anagrafe EstraiAnagraficaDaRispostaParix(string result)
        {
            this._log.DebugFormat("ParixCloudAnagrafeSearcher - EstraiAnagraficaDaRispostaParix: xml={0}", result);

            Parix.ListaImpreseNs.RISPOSTA listaImprese = this.Deserializza<Parix.ListaImpreseNs.RISPOSTA>(result);

            if (listaImprese == null)
            {
                return null;
            }

            if (listaImprese.HEADER.ESITO != "OK")
            {
                // Loggo l'errore restituito da parix e sollevo un'eccezione
                Parix.ListaImpreseNs.RISPOSTADATIERRORE rispostaErrore = (Parix.ListaImpreseNs.RISPOSTADATIERRORE)listaImprese.DATI.Item;

                this._log.ErrorFormat("ParixCloudAnagrafeSearcher - chiamata al wm RicercaImpreseNonCessatePerCodiceFiscale non è andata a buon fine. Codice di errore: {0}.Descrizione errore: {1}", rispostaErrore.TIPO, rispostaErrore.MSG_ERR);

                return null;
            }

            RISPOSTADATILISTA_IMPRESE datiImpresa = ((Parix.ListaImpreseNs.RISPOSTADATILISTA_IMPRESE)listaImprese.DATI.Item);

            if (datiImpresa.ESTREMI_IMPRESA == null || datiImpresa.ESTREMI_IMPRESA.Length == 0)
                return null;

            return this.AdattaAnagrafica(datiImpresa.ESTREMI_IMPRESA[0]);
        }

        public Anagrafe ByCodiceFiscaleImp(string codiceFiscale) => this._searcherSigepro.ByCodiceFiscaleImp(codiceFiscale);

        public Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return this._searcherSigepro.ByCodiceFiscaleImp(codiceFiscale);
            }

            if (tipoPersona == TipoPersona.PersonaGiuridica)
            {
                return this.ByPartitaIvaImp(codiceFiscale);
            }

            return null;
        }

        public List<Anagrafe> ByNomeCognomeImp(string nome, string cognome) => this._searcherSigepro.ByNomeCognomeImp(nome, cognome);

        /// <summary>
        /// Verifica le variazioni delle anagrafiche in un determinato periodo di tempo
        /// </summary>
        /// <param name="from"></param>
        /// <param name="to"></param>
        /// <returns></returns>
        public virtual IEnumerable<Anagrafe> GetVariazioni(DateTime from, DateTime to)
        {
            throw new NotImplementedException("METODO GetVariazioni non implementato");
        }

        /// <summary>
        /// Permette di effettuare la pulizia delle risorse utilizzate dal componente. 
        /// E' sempre chiamato dopo l'invocazione di <see cref="ByCodiceFiscaleImp"/> e di <see cref="ByPartitaIvaImp"/>
        /// </summary>
        public virtual void CleanUp()
        {
        }

        /// <summary>
        /// Permette di effettuare l'inizializzazione del componente. 
        /// E' sempre chiamato prima dell'invocazione di <see cref="ByCodiceFiscaleImp"/> e di <see cref="ByPartitaIvaImp"/>
        /// </summary>
        public virtual void Init()
        {
        }
    }
}
