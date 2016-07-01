using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Exceptions.Protocollo;
using log4net;
using Init.SIGePro.Verticalizzazioni;
using System.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.IO;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.JProtocollo.Proxies;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{

    public class PROTOCOLLO_JPROTOCOLLO : ProtocolloBase
    {

        #region Proprietà interne della classe

        ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_JPROTOCOLLO));
        VerticalizzazioneProtocolloJprotocollo _vertJProtocolloJProtocollo = null;
        VerticalizzazioneProtocolloAttivo _vertProtocolloAttivo = null;

        Byte[] _file = null;
        string _codiceFormato = String.Empty;
        string _descrizioneFormato = String.Empty;
        string _nomeFile = String.Empty;
        string _titolo = String.Empty;
        string _codiceVolume = String.Empty;
        string _descrizioneVolume = String.Empty;

        #endregion

        #region Costruttori

        public PROTOCOLLO_JPROTOCOLLO()
        {

        }

        #endregion

        #region Costanti

        private const string ESITO_OK = "OK";
        private const string ESITO_KO = "KO";

        #endregion

        #region Creazione Web Service e Verticalizzazioni

        private ProxyProtJProtocollo CreaWebService(string url)
        {
            try
            {
                var ws = new ProxyProtJProtocollo(url);
                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEL WEB SERVICE", ex);
            }
        }

        private ProxyProtJProtocollo AttivaConnessioneWs()
        {
            try
            {
                _vertJProtocolloJProtocollo = GetParamFromVertJProtocollo();
                _vertProtocolloAttivo = GetParamFromVertAttivo();

                if (!_vertJProtocolloJProtocollo.Attiva)
                    throw new Exception("Verticalizzazione PROTOCOLLO_JPROTOCOLLO non attiva.");

                if (String.IsNullOrEmpty(_vertJProtocolloJProtocollo.Url))
                    throw new Exception("Il parametro Url della verticalizzazione PROTOCOLLO_JPROTOCOLLO non è stato valorizzato.");

                return CreaWebService(_vertJProtocolloJProtocollo.Url);
            }
            catch (Exception)
            {
                throw;
            }
        }

        #endregion

        #region Protocollazione

        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn pProt)
        {
            var res = new DatiProtocolloRes();
            try
            {
                using (ProxyProtJProtocollo jProtocolloRef = AttivaConnessioneWs())
                {
                    if (pProt.Flusso == ProtocolloConstants.COD_ARRIVO)
                        res = InserisciEntrata(pProt, jProtocolloRef);

                    if (pProt.Flusso == ProtocolloConstants.COD_PARTENZA)
                        res = InserisciUscita(pProt, jProtocolloRef);

                    if (pProt.Flusso == ProtocolloConstants.COD_INTERNO)
                        res = InserisciInterno(pProt, jProtocolloRef);
                }
                
                return res;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex.Message), ex);
            }
        }

        private DatiProtocolloRes InserisciEntrata(DatiProtocolloIn protoIn, ProxyProtJProtocollo jProtocolloRef)
        {
            var protocollo = new inserisciArrivoRichiestaProtocollaArrivo();
            protocollo.oggetto = protoIn.Oggetto;

            var richiesta = new inserisciArrivoRichiesta();
            richiesta.username = GetUsername();

            #region Impostazione degli Allegati

            if (protoIn.Allegati.Count > 0)
            {
                SetAllegato(protoIn);

                var documento = new inserisciArrivoRichiestaDocumento
                {
                    titolo = _titolo,
                    file = _file,
                    nomeFile = _nomeFile,

                };

                richiesta.documento = documento;
            }

            #endregion

            #region Impostazione dei Mittenti

            var mittenti = GetMittentiArrivo(protoIn, protoIn.Mittenti);

            var soggetti = new inserisciArrivoRichiestaProtocollaArrivoSoggetti { Items = mittenti.ToArray() };

            protocollo.soggetti = soggetti;

            #endregion

            #region Impostazione del Destinatario (Smistamento)

            //Il destinatario interno viene valorizzato sul campo Unità Organizzativa presente sulle Amministrazioni

            if (protoIn.Destinatari.Amministrazione.Count == 0)
                throw new Exception("Destinatario non valorizzato");

            var codiceDestinatarioInterno = protoIn.Destinatari.Amministrazione[0].PROT_UO;

            if (String.IsNullOrEmpty(codiceDestinatarioInterno))
                throw new Exception("Non è stato trovato il destinatario interno, va valorizzato il campo Unità Organizzativa dell'amministrazione (Destinatario) selezionata");

            var corrispondenteDestinatario = new inserisciArrivoRichiestaProtocollaArrivoSmistamentoCorrispondente { codice = codiceDestinatarioInterno };
            var listDestinatariInterni = new List<inserisciArrivoRichiestaProtocollaArrivoSmistamento>();
            listDestinatariInterni.Add(new inserisciArrivoRichiestaProtocollaArrivoSmistamento { corrispondente = corrispondenteDestinatario });


            protocollo.smistamenti = listDestinatariInterni.ToArray();

            #endregion

            #region AltriDati - Tramite

            var tramite = new inserisciArrivoRichiestaProtocollaArrivoAltriDatiTramite { codice = GetTramite(protoIn) };

            protocollo.altriDati = new inserisciArrivoRichiestaProtocollaArrivoAltriDati { tramite = tramite };

            #endregion

            #region Classifica

            protocollo.classificazione = new inserisciArrivoRichiestaProtocollaArrivoClassificazione { titolario = GetClassifica(protoIn.Classifica) };

            #endregion

            #region Tipo Documento

            protocollo.altriDati.tipoDocumento = new inserisciArrivoRichiestaProtocollaArrivoAltriDatiTipoDocumento { codice = GetTipoDocumento(protoIn.TipoDocumento) };

            #endregion

            #region Risposta

            richiesta.protocollaArrivo = protocollo;
                
            _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, richiesta, ProtocolloValidation.TipiValidazione.XSD, "", true);
            _protocolloLogs.InfoFormat("Richiesta al web method inserisciArrivo (protocollazione),  file richiesta: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
            var wsResponse = jProtocolloRef.inserisciArrivo(richiesta);
            _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, wsResponse);

            if (wsResponse.esito == ESITO_KO)
                throw new Exception(String.Format("MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO: {0}", wsResponse.messaggio));

            _protocolloLogs.Info("PROTOCOLLAZIONE IN ENTRATA AVVENUTA CON SUCCESSO");

            var protoRes = new DatiProtocolloRes();

            protoRes.AnnoProtocollo = wsResponse.segnatura.anno;
            protoRes.DataProtocollo = wsResponse.segnatura.data;
            protoRes.NumeroProtocollo = wsResponse.segnatura.numero;

            if (AggiungiAnno)
                protoRes.NumeroProtocollo += "/" + protoRes.AnnoProtocollo;

            _protocolloLogs.InfoFormat("Dati protocollo in entrata restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

            return protoRes;

            #endregion
                
        }

        private DatiProtocolloRes InserisciUscita(DatiProtocolloIn protoIn, ProxyProtJProtocollo jProtocolloRef)
        {
            var protocollo = new inserisciPartenzaRichiestaProtocollaPartenza();
            protocollo.oggetto = protoIn.Oggetto;

            var richiesta = new inserisciPartenzaRichiesta();
            richiesta.username = GetUsername();

            #region Impostazione degli Allegati

            if (protoIn.Allegati.Count > 0)
            {
                SetAllegato(protoIn);

                var documento = new inserisciPartenzaRichiestaDocumento
                {
                    titolo = _titolo,
                    file = _file,
                    nomeFile = _nomeFile
                };

                richiesta.documento = documento;
            }

            #endregion

            #region Impostazione del Mittente
            //Il mittente interno viene valorizzato sul campo Unità Organizzativa presente sulle Amministrazioni

            if (String.IsNullOrEmpty(protoIn.Mittenti.Amministrazione[0].PROT_UO))
                throw new Exception("Non è stato trovato il mittente interno, va valorizzato il campo Unità Organizzativa dell'amministrazione (Mittente) selezionata");

            var codiceMittenteInterno = protoIn.Mittenti.Amministrazione[0].PROT_UO;

            var corrispondenteMittente = new inserisciPartenzaRichiestaProtocollaPartenzaMittenteInternoCorrispondente { codice = codiceMittenteInterno };
            var mittenteInterno = new inserisciPartenzaRichiestaProtocollaPartenzaMittenteInterno { corrispondente = corrispondenteMittente };

            protocollo.mittenteInterno = mittenteInterno;

            #endregion

            #region Impostazione dei Destinatari e Destinatari CC

            var destinatari = GetDestinatariPartenza(protoIn, protoIn.Destinatari);
            var destinatariInterni = GetDestinatariInterniPartenza(protoIn, protoIn.Destinatari);
            //var destinatariCC = GetDestinatariPartenza(pProt, pProt.DestinatariPerConoscenza);

            //(inserisciPartenzaRichiestaProtocollaPartenzaSoggettiAltriSoggetti[])

            var soggetti = new inserisciPartenzaRichiestaProtocollaPartenzaSoggetti { Items = destinatari.ToArray() };
            //var corrispondenti = new inserisciPartenzaRichiestaProtocollaPartenzaSmistamentoCorrispondente { codice = pProt.Destinatari.Amministrazione[0].PROT_UO };

            /*if (destinatariCC.Count > 0)
                soggetti.altriSoggetti = (inserisciPartenzaRichiestaProtocollaPartenzaSoggettiAltriSoggetti[])destinatariCC.ToArray();*/

            protocollo.soggetti = soggetti;
            protocollo.smistamenti = destinatariInterni.ToArray();

            #endregion

            #region AltriDati - Tramite

            var tramite = new inserisciPartenzaRichiestaProtocollaPartenzaAltriDatiTramite { codice = GetTramite(protoIn) };

            protocollo.altriDati = new inserisciPartenzaRichiestaProtocollaPartenzaAltriDati { tramite = tramite };

            #endregion

            #region Classifica

            protocollo.classificazione = new inserisciPartenzaRichiestaProtocollaPartenzaClassificazione { titolario = GetClassifica(protoIn.Classifica) };

            #endregion

            #region Tipo Documento

            protocollo.altriDati.tipoDocumento = new inserisciPartenzaRichiestaProtocollaPartenzaAltriDatiTipoDocumento { codice = GetTipoDocumento(protoIn.TipoDocumento) };

            #endregion

            #region Risposta

                richiesta.protocollaPartenza = protocollo;

                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, richiesta, ProtocolloValidation.TipiValidazione.XSD, "", true);
                _protocolloLogs.InfoFormat("Richiesta al web method inserisciPartenza (protocollazione),  file richiesta: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                var wsResponse = jProtocolloRef.inserisciPartenza(richiesta);
                
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, wsResponse);

                if (wsResponse.esito == ESITO_KO)
                    throw new Exception(String.Format("PROTOCOLLAZIONE IN USCITA, MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO: {0}", wsResponse.messaggio));

                _protocolloLogs.Info("PROTOCOLLAZIONE IN USCITA AVVENUTA CON SUCCESSO");

                var protoRes = new DatiProtocolloRes();

                protoRes.AnnoProtocollo = wsResponse.segnatura.anno;
                protoRes.DataProtocollo = wsResponse.segnatura.data;
                protoRes.NumeroProtocollo = wsResponse.segnatura.numero;

                if (AggiungiAnno)
                    protoRes.NumeroProtocollo += "/" + protoRes.AnnoProtocollo;

                _protocolloLogs.InfoFormat("Dati protocollo in uscita restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

                _protocolloLogs.DebugFormat("Fine funzionalità di protocollazione in uscita {0}", String.IsNullOrEmpty(DatiProtocollo.CodiceIstanza) ? String.Concat(" del movimento ", DatiProtocollo.CodiceMovimento) : String.Concat(" dell'istanza ", DatiProtocollo.CodiceIstanza));

                return protoRes;

                #endregion
        }

        private DatiProtocolloRes InserisciInterno(DatiProtocolloIn protoIn, ProxyProtJProtocollo jProtocolloRef)
        {
            var protocollo = new inserisciInternoRichiestaProtocollaInterno();
            protocollo.oggetto = protoIn.Oggetto;

            var richiesta = new inserisciInternoRichiesta();
            richiesta.username = GetUsername();

            #region Impostazione del Mittente
            //Il mittente interno viene valorizzato sul campo Unità Organizzativa presente sulle Amministrazioni

            if (String.IsNullOrEmpty(protoIn.Mittenti.Amministrazione[0].PROT_UO))
                throw new Exception("Non è stato trovato il mittente interno, va valorizzato il campo Unità Organizzativa dell'amministrazione (Mittente) selezionata");

            var codiceMittenteInterno = protoIn.Mittenti.Amministrazione[0].PROT_UO;

            var corrispondenteMittente = new inserisciInternoRichiestaProtocollaInternoMittenteInternoCorrispondente { codice = codiceMittenteInterno };
            var mittenteInterno = new inserisciInternoRichiestaProtocollaInternoMittenteInterno { corrispondente = corrispondenteMittente };

            protocollo.mittenteInterno = mittenteInterno;

            #endregion

            #region Impostazione del Destinatario (Smistamento)

            //Il destinatario interno viene valorizzato sul campo Unità Organizzativa presente sulle Amministrazioni

            var codiceDestinatarioInterno = protoIn.Mittenti.Amministrazione[0].PROT_UO;

            //Commentata perchè in questo momento  le amministrazioni utilizzate sono una sola, quindi, in pratica, 
            //non potrebbero mai fare una protocollo interno se così fosse, e poi non è controllato nemmeno sulla parte java.
            /*if (codiceMittenteInterno == codiceDestinatarioInterno)
                throw new Exception("Il mittente è uguale al destinatario");*/

            if (String.IsNullOrEmpty(codiceDestinatarioInterno))
                throw new Exception("Non è stato trovato il destinatario interno, va valorizzato il campo Unità Organizzativa dell'amministrazione (Destinatario) selezionata");

            var corrispondenteDestinatario = new inserisciInternoRichiestaProtocollaInternoSmistamentoCorrispondente { codice = codiceDestinatarioInterno };
            var listDestinatariInterni = new List<inserisciInternoRichiestaProtocollaInternoSmistamento>();
            listDestinatariInterni.Add(new inserisciInternoRichiestaProtocollaInternoSmistamento { corrispondente = corrispondenteDestinatario });

            protocollo.smistamenti = listDestinatariInterni.ToArray();

            #endregion

            #region AltriDati - Tramite

            var tramite = new inserisciInternoRichiestaProtocollaInternoAltriDatiTramite { codice = GetTramite(protoIn) };

            protocollo.altriDati = new inserisciInternoRichiestaProtocollaInternoAltriDati { tramite = tramite };

            #endregion

            #region Classifica

            protocollo.classificazione = new inserisciInternoRichiestaProtocollaInternoClassificazione { titolario = GetClassifica(protoIn.Classifica) };

            #endregion

            #region Tipo Documento

            protocollo.altriDati.tipoDocumento = new inserisciInternoRichiestaProtocollaInternoAltriDatiTipoDocumento { codice = GetTipoDocumento(protoIn.TipoDocumento) };

            #endregion

            #region Risposta

            richiesta.protocollaInterno = protocollo;

            _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, richiesta, ProtocolloValidation.TipiValidazione.XSD, "", true);
            _protocolloLogs.InfoFormat("Richiesta al web method inserisciInterno (protocollazione),  file richiesta: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);

            inserisciInternoResponseReturn wsResponse = jProtocolloRef.inserisciInterno(richiesta);
            _protocolloLogs.DebugFormat("Risposta ottenuta dal web method inserisciInterno del web service {0}", String.IsNullOrEmpty(DatiProtocollo.CodiceIstanza) ? String.Concat(" del movimento ", DatiProtocollo.CodiceMovimento) : String.Concat(" dell'istanza ", DatiProtocollo.CodiceIstanza));
            _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, wsResponse);

            if (wsResponse.esito == ESITO_KO)
                throw new Exception(String.Format("PROTOCOLLAZIONE INTERNA, MESSAGGIO DI ERRORE RESTITUITO DA JPROTOCOLLO: {0}", wsResponse.messaggio));

            _protocolloLogs.Info("PROTOCOLLAZIONE INTERNA AVVENUTA CON SUCCESSO");

            var protoRes = new DatiProtocolloRes();

            protoRes.AnnoProtocollo = wsResponse.segnatura.anno;
            protoRes.DataProtocollo = wsResponse.segnatura.data;
            protoRes.NumeroProtocollo = wsResponse.segnatura.numero;

            if (AggiungiAnno)
                protoRes.NumeroProtocollo += "/" + protoRes.AnnoProtocollo;

            _protocolloLogs.InfoFormat("Dati protocollo interno restituiti, numero: {0}, anno: {1}, data: {2}", protoRes.NumeroProtocollo, protoRes.AnnoProtocollo, protoRes.DataProtocollo);

            _protocolloLogs.DebugFormat("Fine funzionalità di protocollazione in uscita {0}", String.IsNullOrEmpty(DatiProtocollo.CodiceIstanza) ? String.Concat(" del movimento ", DatiProtocollo.CodiceMovimento) : String.Concat(" dell'istanza ", DatiProtocollo.CodiceIstanza));

            return protoRes;

            #endregion
        }

        #endregion

        #region Gestione Allegato / Destinatari Partenza / Mittenti Arrivo

        /// <summary>
        /// Setta l'allegato, da controllare, prima di invocare il metodo, che il numero degli allegati sia maggiore di 0.
        /// Se maggiore di uno restituisce un'eccezione in quanto è possibile inserire un solo allegato o nessun allegato.
        /// </summary>
        /// <param name="protoIn"></param>
        private void SetAllegato(DatiProtocolloIn protoIn)
        {

            try
            {
                if (protoIn.Allegati.Count > 1)
                    throw new Exception("E' presente più di un allegato, è possibile inserire solamente 1 allegato, la protocollazione non è andata a buon fine.");

                var all = protoIn.Allegati[0];

                int iPos = all.Descrizione.LastIndexOf("." + all.Extension);

                if (iPos != -1)
                {
                    _nomeFile = all.Descrizione;
                    _titolo = all.Descrizione.Substring(0, iPos);
                }
                else
                {
                    _nomeFile = all.NOMEFILE;
                    iPos = all.NOMEFILE.LastIndexOf("." + all.Extension);

                    _titolo = String.IsNullOrEmpty(all.Descrizione) ? all.NOMEFILE.Substring(0, iPos) : all.Descrizione;
                }
                _file = all.OGGETTO;

            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEGLI ALLEGATI", ex);
            }
        }

        /// <summary>
        /// Ottiene i destinatari anagrafici della protocollazione in partenza, è possibile utilizzare questo metodo anche per i 
        /// destinatari per conoscenza, passando pProt.DestinatariPerConoscenza sul parametro list.
        /// </summary>
        /// <param name="pProt"></param>
        /// <param name="list"></param>
        /// <returns></returns>
        private List<object> GetDestinatariPartenza(Data.DatiProtocolloIn pProt, ListaMittDest list)
        {
            try
            {
                List<object> listDestinatari = new List<object>();
                foreach (var anag in list.Anagrafe)
                {
                    var denominazione = anag.TIPOANAGRAFE == "F" ? anag.NOME + " " + anag.NOMINATIVO : anag.NOMINATIVO;
                    var soggetto = new inserisciPartenzaRichiestaProtocollaPartenzaSoggettiSoggetto { denominazione = denominazione, indirizzo = anag.INDIRIZZO };

                    listDestinatari.Add(soggetto);
                }

                var smistamenti = new List<inserisciPartenzaRichiestaProtocollaPartenzaSmistamento>();

                foreach (var amm in list.Amministrazione)
                {
                    if (String.IsNullOrEmpty(amm.PROT_UO))
                    {
                        var soggettoAmministrazione = new inserisciPartenzaRichiestaProtocollaPartenzaSoggettiSoggetto
                        {
                            denominazione = amm.AMMINISTRAZIONE,
                            indirizzo = amm.INDIRIZZO
                        };

                        listDestinatari.Add(soggettoAmministrazione);
                    }
                }

                return listDestinatari;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI", ex);
            }
        }

        /// <summary>
        /// Ottiene i destinatari interni della protocollazione in partenza, è possibile utilizzare questo metodo anche per i 
        /// destinatari per conoscenza, passando pProt.DestinatariPerConoscenza sul parametro list.
        /// </summary>
        /// <param name="pProt"></param>
        /// <param name="list"></param>
        /// <returns></returns>
        private List<inserisciPartenzaRichiestaProtocollaPartenzaSmistamento> GetDestinatariInterniPartenza(Data.DatiProtocolloIn pProt, ListaMittDest list)
        {
            try
            {
                var smistamenti = new List<inserisciPartenzaRichiestaProtocollaPartenzaSmistamento>();

                foreach (var amm in list.Amministrazione)
                {
                    if (!String.IsNullOrEmpty(amm.PROT_UO))
                    {
                        var corrispondenteDestinatario = new inserisciPartenzaRichiestaProtocollaPartenzaSmistamentoCorrispondente { codice = amm.PROT_UO };
                        smistamenti.Add(new inserisciPartenzaRichiestaProtocollaPartenzaSmistamento { corrispondente = corrispondenteDestinatario });
                    }
                }

                return smistamenti;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEI DESTINATARI INTERNI", ex);
            }
        }

        /// <summary>
        /// Ottiene i mittenti della protocollazione in arrivo.
        /// </summary>
        /// <param name="pProt"></param>
        /// <param name="list"></param>
        /// <returns></returns>
        private List<object> GetMittentiArrivo(Data.DatiProtocolloIn pProt, ListaMittDest list)
        {
            try
            {
                List<object> listMittenti = new List<object>();
                foreach (ProtocolloAnagrafe anag in list.Anagrafe)
                {
                    var denominazione = anag.TIPOANAGRAFE == "F" ? anag.NOME + " " + anag.NOMINATIVO : anag.NOMINATIVO;
                    var soggetto = new inserisciArrivoRichiestaProtocollaArrivoSoggettiSoggetto { denominazione = denominazione, indirizzo = anag.INDIRIZZO };
                    
                    listMittenti.Add(soggetto);
                }

                return listMittenti;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL SETTAGGIO DEI MITTENTI IN ARRIVO", ex);
            }
        }

        #endregion

        #region Gestione Tramite / Classifica / TipoDocumento / Username

        /// <summary>
        /// Ottiene il tramite controllando se è stato impostato.
        /// </summary>
        /// <returns></returns>
        private string GetTramite(DatiProtocolloIn protoIn)
        {
            string tramiteDef = String.IsNullOrEmpty(protoIn.TipoSmistamento) ? _vertProtocolloAttivo.Tiposmistamentodefault : protoIn.TipoSmistamento;
            
            if (String.IsNullOrEmpty(tramiteDef))
                tramiteDef = _vertJProtocolloJProtocollo.Tramitedefault;

            if (String.IsNullOrEmpty(tramiteDef))
                throw new Exception("Il tramite di default non è stato inserito, va popolato il parametro TRAMITEDEFAULT della verticalizzazione PROTOCOLLO_JPROTOCOLLO");

            return tramiteDef;
        }

        /// <summary>
        /// Ottiene la classifica controllando se è stata impostata.
        /// </summary>
        /// <param name="classifica"></param>
        /// <returns></returns>
        private string GetClassifica(string classifica)
        {
            if (String.IsNullOrEmpty(classifica))
                throw new Exception("La classifica non è stata valorizzata");

            return classifica;
        }

        /// <summary>
        /// Ottiene il tipo documento controllando se è stato impostato.
        /// </summary>
        /// <param name="tipoDocumento"></param>
        /// <returns></returns>
        private string GetTipoDocumento(string tipoDocumento)
        {
            if (String.IsNullOrEmpty(tipoDocumento))
                throw new Exception("Il tipo documento non è stato valorizzato");

            return tipoDocumento;
        }

        /// <summary>
        /// Ottiene lo username controllando se è stato impostato.
        /// </summary>
        /// <returns></returns>
        private string GetUsername()
        {
            //string username = _vertJProtocolloJProtocollo.Username;
            string username = Operatore;
            if (String.IsNullOrEmpty(username))
                throw new Exception("USERNAME NON IMPOSTATO, IL CODICE FISCALE DELL'OPERATORE E' OBBLIGATORIO PER EFFETTUARE UNA PROTOCOLLAZIONE");

            return username;
        }

        #endregion

        #region Lettura Protocollo

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                _protocolloLogs.DebugFormat("Inizio funzionalità di lettura del protocollo numero: {0}, anno {1}, id: {2}", numeroProtocollo, annoProtocollo, idProtocollo);
                using (ProxyProtJProtocollo jProtocolloRef = AttivaConnessioneWs())
                {
                    leggiProtocolloRichiesta richiesta = new leggiProtocolloRichiesta();

                    if (numeroProtocollo.IndexOf("/") > -1)
                    {
                        string[] arrNumProto = numeroProtocollo.Split('/');
                        numeroProtocollo = arrNumProto[0];
                    }

                    richiesta.username = GetUsername();
                    richiesta.riferimento = new leggiProtocolloRichiestaRiferimento
                    {
                        anno = annoProtocollo,
                        numero = numeroProtocollo
                    };

                    //Se = false viene restituito solo l'indice dell'allegato, altrimenti restituisce tutto il file.
                    richiesta.allegati = true;
                    
                    _protocolloLogs.InfoFormat("Chiamata a web method leggiProtocollo, id protocollo: {0}, anno: {1}, numero: {2}", idProtocollo, annoProtocollo, numeroProtocollo);
                    leggiProtocolloResponseReturn wsResponse = jProtocolloRef.leggiProtocollo(richiesta);

                    if (wsResponse.esito == ESITO_KO)
                        throw new Exception("Si è verificato un errore durante la la lettura del protocollo, Messaggio di errore restituito da JPROTOCOLLO: " + wsResponse.messaggio);

                    DatiProtocolloLetto res = SetDatiProtocolloLetto(wsResponse);

                    _protocolloLogs.Info("LETTURA PROTOCOLLO AVVENUTA CON SUCCESSO");

                    _protocolloLogs.DebugFormat("Fine funzionalità di lettura del protocollo numero: {0}, anno {1}, id: {2}", numeroProtocollo, annoProtocollo, idProtocollo);

                    return res;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
            }
        }

        public override AllOut LeggiAllegato()
        {
            return base.GetAllegato();
        }

        private DatiProtocolloLetto SetDatiProtocolloLetto(leggiProtocolloResponseReturn wsResponse)
        {
            try
            {
                DatiProtocolloLetto res = new DatiProtocolloLetto();

                res.AnnoProtocollo = wsResponse.protocollo.anno;
                res.NumeroProtocollo = wsResponse.protocollo.numero;
                res.DataProtocollo = DateTime.ParseExact(wsResponse.protocollo.dataArrivoPartenza, "yyyyMMdd", null).ToString("dd/MM/yyyy");
                res.Oggetto = wsResponse.protocollo.oggetto;
                res.Origine = wsResponse.protocollo.tipo;

                bool seAnnullato = wsResponse.protocollo.annullamento != null && wsResponse.protocollo.annullamento.annullato;

                res.Annullato = seAnnullato ? "SI" : "NO";

                if (seAnnullato)
                    res.MotivoAnnullamento = wsResponse.protocollo.annullamento.atto;

                if (wsResponse.protocollo.classificazione != null)
                {
                    if (!String.IsNullOrEmpty(wsResponse.protocollo.classificazione.titolario))
                    {
                        res.Classifica = wsResponse.protocollo.classificazione.titolario;
                        res.Classifica_Descrizione = wsResponse.protocollo.classificazione.titolario;

                        if (!String.IsNullOrEmpty(wsResponse.protocollo.classificazione.descrizione))
                            res.Classifica_Descrizione += " - " + wsResponse.protocollo.classificazione.descrizione;
                    }
                }

                if (wsResponse.protocollo.altriDati != null && wsResponse.protocollo.altriDati.tipoDocumento != null)
                {
                    if (!String.IsNullOrEmpty(wsResponse.protocollo.altriDati.tipoDocumento.codice))
                        res.TipoDocumento = wsResponse.protocollo.altriDati.tipoDocumento.codice;

                    if (!String.IsNullOrEmpty(wsResponse.protocollo.altriDati.tipoDocumento.descrizione))
                        res.TipoDocumento_Descrizione = wsResponse.protocollo.altriDati.tipoDocumento.descrizione;

                }

                if (wsResponse.protocollo.mittenteInterno != null && wsResponse.protocollo.mittenteInterno.corrispondente != null)
                {
                    if (!String.IsNullOrEmpty(wsResponse.protocollo.mittenteInterno.corrispondente.codice))
                        res.MittenteInterno = wsResponse.protocollo.mittenteInterno.corrispondente.codice;

                    if (!String.IsNullOrEmpty(wsResponse.protocollo.mittenteInterno.corrispondente.descrizione))
                        res.MittenteInterno_Descrizione = wsResponse.protocollo.mittenteInterno.corrispondente.descrizione;

                }

                var listMittDest = new List<MittDestOut>();

                if (wsResponse.protocollo.smistamenti != null)
                {
                    string inCaricoACodice = String.Empty;
                    string inCaricoADescrizione = String.Empty;

                    foreach (leggiProtocolloResponseReturnProtocolloSmistamento sm in wsResponse.protocollo.smistamenti)
                    {
                        if (sm.corrispondente != null)
                        {
                            if (wsResponse.protocollo.tipo == ProtocolloConstants.COD_ARRIVO || wsResponse.protocollo.tipo == ProtocolloConstants.COD_INTERNO)
                            {
                                if (!String.IsNullOrEmpty(sm.corrispondente.codice))
                                    inCaricoACodice += sm.corrispondente.codice + "; ";

                                if (!String.IsNullOrEmpty(sm.corrispondente.descrizione))
                                    inCaricoADescrizione += sm.corrispondente.descrizione + "; ";
                            }
                            else if (wsResponse.protocollo.tipo == ProtocolloConstants.COD_PARTENZA)
                            {
                                var mittDest = new MittDestOut();
                                mittDest.CognomeNome = String.Format("{0} - (DESTINATARIO INTERNO)", sm.corrispondente.descrizione);
                                mittDest.IdSoggetto = sm.corrispondente.codice;

                                listMittDest.Add(mittDest);
                            }
                        }
                    }

                    if (!String.IsNullOrEmpty(inCaricoACodice))
                        res.InCaricoA = inCaricoACodice.Substring(0, inCaricoACodice.Length - 2);

                    if (!String.IsNullOrEmpty(inCaricoADescrizione))
                        res.InCaricoA_Descrizione = inCaricoADescrizione.Substring(0, inCaricoADescrizione.Length - 2);
                }
                if (!String.IsNullOrEmpty(wsResponse.protocollo.dataRegistrazione))
                    res.DataProtocollo = wsResponse.protocollo.dataRegistrazione;

                //Sezione Mittenti/Destinatari
                if (wsResponse.protocollo.soggetti != null)
                {
                    //res.MittentiDestinatari = new MittDestOut[wsResponse.protocollo.soggetti.Items.Length];
                    var soggetti = wsResponse.protocollo.soggetti.Items;
                    //int iIndex = 0;
                    for (int i = 0; i < soggetti.Length; i++)
                    {
                        //string denominazioneSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).denominazione;
                        //string indirizzoSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).indirizzo;

                        string denominazioneSoggetto = String.Empty;
                        string indirizzoSoggetto = String.Empty;

                        if (soggetti[i] is leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)
                        {
                            denominazioneSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).denominazione;
                            indirizzoSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).indirizzo;
                        }
                        else if (soggetti[i] is leggiProtocolloResponseReturnProtocolloSoggettiAnagrafica)
                        {
                            denominazioneSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).denominazione;
                            indirizzoSoggetto = ((leggiProtocolloResponseReturnProtocolloSoggettiSoggetto)soggetti[i]).indirizzo;
                        }

                        //res.MittentiDestinatari[i] = new MittDestOut();

                        _log.DebugFormat("Tipo del Soggetto: {0}", soggetti[i].GetType().ToString());

                        if (!String.IsNullOrEmpty(denominazioneSoggetto))
                        {
                            var mittDest = new MittDestOut();

                            mittDest.IdSoggetto = " - ";
                            mittDest.CognomeNome = denominazioneSoggetto;

                            if (!String.IsNullOrEmpty(indirizzoSoggetto))
                                mittDest.CognomeNome += ", " + indirizzoSoggetto;

                            if (wsResponse.protocollo.tipo == ProtocolloConstants.COD_ARRIVO)
                                mittDest.CognomeNome += " - (MITTENTE)";

                            if (wsResponse.protocollo.tipo == ProtocolloConstants.COD_PARTENZA)
                                mittDest.CognomeNome += " - (DESTINATARIO)";

                            listMittDest.Add(mittDest);
                        }
                    }
                }

                res.MittentiDestinatari = listMittDest.ToArray();

                if (_vertProtocolloAttivo.Noallegati != "1" && wsResponse.protocollo.documenti != null)
                {
                    if (wsResponse.protocollo.documenti.Length > 0)
                    {
                        List<AllOut> listAll = new List<AllOut>();
                        foreach (leggiProtocolloResponseReturnProtocolloDocumento doc in wsResponse.protocollo.documenti)
                        {
                            var all = new AllOut();
                            all.Image = doc.file;
                            all.Serial = doc.nomeFile;
                            all.Commento = doc.titolo;
                            all.IDBase = doc.nomeFile;
                            all.ContentType = new OggettiMgr(DatiProtocollo.Db).GetContentType(doc.nomeFile);
                            all.TipoFile = Path.GetExtension(doc.nomeFile);
                            listAll.Add(all);
                        }

                        res.Allegati = listAll.ToArray();
                    }
                }
                return res;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA VALORIZZAZIONE DEI DATI DOPO LA LETTURA DEL PROTOCOLLO", ex);
            }
        }
        #endregion

        #region Verticalizzazioni

        private VerticalizzazioneProtocolloJprotocollo GetParamFromVertJProtocollo()
        {
            try
            {
                var vertJProtocollo = new VerticalizzazioneProtocolloJprotocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (!vertJProtocollo.Attiva)
                    throw new ProtocolloException("La verticalizzazione PROTOCOLLO_JPROTOCOLLO non è attiva");

                return vertJProtocollo;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA LETTURA DELLA VERTICALIZZAZIONE PROTOCOLLO_JPROTOCOLLO", ex);
            }
        }

        private VerticalizzazioneProtocolloAttivo GetParamFromVertAttivo()
        {
            try
            {
                var vertProtocolloAttivo = new VerticalizzazioneProtocolloAttivo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (!vertProtocolloAttivo.Attiva)
                    throw new ProtocolloException("La verticalizzazione PROTOCOLLO_ATTIVO non è attiva.\r\n");

                return vertProtocolloAttivo;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA LETTURA DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO", ex);
            }
        }

        #endregion

    }
}
