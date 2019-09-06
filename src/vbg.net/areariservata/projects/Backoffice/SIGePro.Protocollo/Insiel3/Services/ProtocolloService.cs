using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo.Insiel3.Services
{
    public class ProtocolloService : BaseService
    {
        Utente _utente;

        public ProtocolloService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string utente) : base(url, logs, serializer)
        {
            _utente = new Utente { Item = utente, ItemElementName = ItemChoiceType.codice };
        }

        private ProtocolloPTClient CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice di protocollazione INSIEL3");

                var endPointAddress = new EndpointAddress(Url);
                var binding = new BasicHttpBinding("insielUploadHttpBinding");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new ProtocolloPTClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del web service di protocollazione INSIEL2");

                return ws;

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        internal void AggiornaAnagrafica(AggiornamentoAnagraficaRequest request)
        {
            using (var ws = CreaWebService())
            {
                request.utente = _utente;

                var requestSerializzata = Serializer.Serialize(ProtocolloLogsConstants.UpdateAnagraficaRequest, request);
                Logs.InfoFormat("AGGIORNAMENTO ANAGRAFICA, XML RICHIESTA: {0}", requestSerializzata);

                var response = ws.aggiornaAnagrafica(request);

                if (response == null)
                {
                    Logs.Warn("LA RISPOSTA A INSERIMENTO NUOVA ANAGRAFICA E' NULL");
                    return;
                }

                var responseSerializzata = Serializer.Serialize(ProtocolloLogsConstants.UpdateAnagraficaResponse, response);
                Logs.InfoFormat("AGGIORNAMENTO ANAGRAFICA, XML RISPOSTA: {0}", requestSerializzata);

                if (!response.esito)
                {
                    Logs.WarnFormat("ERRORE GENERATO DURANTE L'AGGIORNAMENTO DELL'ANAGRAFICA {0}, ERRORE CODICE: {1}, DESCRIZIONE: {2}", request.idAnagrafica.descAna, response.errore.codice, response.errore.descrizione);
                    return;
                }

                Logs.InfoFormat("AGGIORNAMENTO ANAGRAFICA {0} AVVENUTO CON SUCCESSO", request.idAnagrafica.descAna);
            }
        }

        internal IEnumerable<AnagraficaView> LeggiAnagrafiche(InterrogaAnagraficaRequest request)
        {
            using (var ws = CreaWebService())
            {
                request.utente = _utente;

                var requestLog = Serializer.Serialize(ProtocolloLogsConstants.LeggiAnagraficheRequest, request);
                Logs.InfoFormat("RICERCA DELL'ANAGRAFICA, XML RICHIESTA: {0}", requestLog);

                var response = ws.interrogaAnagrafica(request);

                var responseLog = Serializer.Serialize(ProtocolloLogsConstants.LeggiAnagraficheResponse, response);
                Logs.InfoFormat("RISPOSTA DAL WS DI RICERCA ANAGRAFICA, XML: {0}", responseLog);

                if (!response.esito)
                {
                    var err = (Errore)response.Items[0];
                    Logs.WarnFormat("ERRORE GENERATO DURANTE LA RICERCA DELL'ANAGRAFICA {0}, ERRORE CODICE: {1}, DESCRIZIONE: {2}", request.anagrafica.descAna, err.codice, err.descrizione);
                    return null;
                }

                if (response.Items == null)
                    return null;

                if (response.Items.Length > 1)
                    Logs.WarnFormat("SONO STATE TROVATE {0} ANAGRAFICHE PER LA RICERCA SELEZIONATA", response.Items.Length);

                var retVal = response.Items.Select(x => (AnagraficaView)x);
                return retVal;
            }
        }

        internal void InserisciAnagrafica(NuovaAnagraficaRequest request)
        {
            using (var ws = CreaWebService())
            {
                request.utente = _utente;

                var requestSerializzata = Serializer.Serialize(ProtocolloLogsConstants.InsertAnagraficaRequest, request);
                Logs.InfoFormat("INSERIMENTO NUOVA ANAGRAFICA, XML RICHIESTA: {0}", requestSerializzata);

                var response = ws.nuovaAnagrafica(request);

                if (response == null)
                {
                    Logs.Warn("LA RISPOSTA A INSERIMENTO NUOVA ANAGRAFICA E' NULL");
                    return;
                }

                var responseSerializzata = Serializer.Serialize(ProtocolloLogsConstants.InsertAnagraficaResponse, response);
                Logs.InfoFormat("INSERIMENTO NUOVA ANAGRAFICA, XML RISPOSTA: {0}", requestSerializzata);

                if (!response.esito)
                {
                    Logs.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ANAGRAFICA {0}, ERRORE CODICE: {1}, DESCRIZIONE: {2}", request.anagrafica.descAna, response.errore.codice, response.errore.descrizione);
                    return;
                }

                Logs.InfoFormat("INSERIMENTO ANAGRAFICA {0} AVVENUTO CON SUCCESSO", request.anagrafica.denominaz);
            }
        }

        internal ProtocolloResponse Protocolla(InserimentoProtocolloRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.utente = _utente;
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.Info("Chiamata al web method inserisciProtocollo del web service di Protocollazione");
                    var response = ws.inserisciProtocollo(request);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (!response.esito)
                    {
                        var err = (Errore)response.Items[0];
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");

                    return (ProtocolloResponse)response.Items[0];
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE, {0}", ex.Message), ex);
                }
            }
        }

        internal DettagliProtocollo LeggiProtocollo(DettagliProtocolloRequest request, bool sollevaErrore)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.utente = _utente;
                    Logs.Info("CHIAMATA AL WEB SERVICE LEGGI PROTOCOLLO");
                    var response = ws.dettagliProtocollo(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;

                        if (err.codice == "-1000" && !sollevaErrore)
                        {
                            return null;
                        }

                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");

                    return (DettagliProtocollo)response.Item;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
                }
            }
        }

        internal DettaglioAllegato DownloadDocumento(DownloadDocumentoRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    var idProto = (IdProtocollo)request.registrazione.Item;

                    request.utente = _utente;
                    Logs.InfoFormat("CHIAMATA A DOWNLOAD DOCUMENTO DEL PROTOCOLLO ID {0}:{1}, DOCUMENTO ID {2}", idProto.progDoc, idProto.progMovi, request.idDoc);

                    var response = ws.downloadDocumento(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    var retVal = (DettaglioAllegato)response.Item;

                    Logs.InfoFormat("LETTURA DEL DOCUMENTO AVVENUTA CORRETTAMENTE, NOME FILE RESTITUITO: {0}", retVal.name);

                    return retVal;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("ERRORE RESTITUITO AL DOWNLOAD DEL DOCUMENTO {0}", ex.Message), ex);
                }
            }
        }

        internal TipiDocResponse GetTipiDocumento(TipiDocRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.utente = _utente;
                    Logs.Info("Chiamata al web service gettipidocumento");

                    var response = ws.getTipiDoc(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Items[0];
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }
                    Logs.Info("LETTURA DEI TIPI DOCUMENTO AVVENUTA CORRETTAMENTE");

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTO, {0}", ex.Message), ex);
                }
            }
        }

        internal Dettagli GetFascicolo(DettagliPraticaRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.utente = _utente;
                    Logs.Info("RECUPERO DEL FASCICOLO, CHIAMATA A DETTAGLIPRATICA");
                    var response = ws.dettagliPratica(request);

                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("RECUPERO DEL FASCICOLO, CHIAMATA A DETTAGLIPRATICA AAVENUTA CON SUCCESSO");

                    return (Dettagli)response.Item;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        internal void AggiornaProtocollo(AggiornamentoProtocolloRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.utente = _utente;

                    var requestXml = Serializer.Serialize(ProtocolloLogsConstants.AggiornaProtocolloRequestFileName, request);
                    Logs.InfoFormat("AGGIORNAMENTO DEL PROTOCOLLO CON I DATI DEL FASCICOLO, {0}", requestXml);
                    var response = ws.aggiornaProtocollo(request);

                    if (!response.esito)
                    {
                        var err = response.errore;
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", response.errore.codice, response.errore.descrizione));
                    }

                    Logs.Info("AGGIORNAMENTO ESEGUITO CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO L'AGGIORNAMENTO DEL PROTOCOLLO CON I DATI DI FASCICOLAZIONE, {ex.Message}", ex);
            }
        }

        internal bool VerificaAbilitazioneFascicolazione(AbilAperturaPraticaRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.utente = _utente;

                    var requestXml = Serializer.Serialize(ProtocolloLogsConstants.VerificaAbilitazioniFascicolazioneRequestFileName, request);
                    Logs.InfoFormat("VERIFICA ABILITAZIONI PER APERTURA PRATICA (FASCICOLO), CHIAMATA A abilAperturaPratica, request: {0}", requestXml);
                    var response = ws.abilAperturaPratica(request);

                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    return (bool)response.Item;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO LA VERIFICA DELLE ABILITAZIONI UTENTE PER LA FASCICOLAZIONE, {ex.Message}", ex);
            }
        }

        internal PraticaResponse CreaFascicolo(AperturaPraticaRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    request.utente = _utente;

                    var requestXml = Serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request);
                    Logs.InfoFormat("CREAZIONE DEL FASCICOLO, CHIAMATA A aperturaPratica, request: {0}", requestXml);
                    var response = ws.aperturaPratica(request);

                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    var retVal = (PraticaResponse)response.Item;

                    Logs.InfoFormat("CREAZIONE DEL FASCICOLO AVVENUTA CON SUCCESSO, NUMERO FASCICOLO: {0}, SUBNUMERO: {1}, ANNO FASCICOLO: {2}, PROGDOC: {3}, PROGMOVI: {4}, DATA APERTURA: {5}, ANNO: {6}, CODICE REGISTRO: {7}, CODICE UFFICIO: {8}", retVal.numero, retVal.subNumero, retVal.anno, retVal.progDoc, retVal.progMovi, retVal.dataApertura.ToString("dd/MM/yyyy"), retVal.anno, retVal.codiceRegistro, retVal.codiceUfficio);

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        internal DettagliPratica InterrogaPratiche(InterrogazionePraticheRequest request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var xmlRequest = Serializer.Serialize(ProtocolloLogsConstants.InterrogaFascicoliRequestFileName, request);

                    request.utente = _utente;
                    Logs.InfoFormat("INTERROGAZIONE DEL FASCICOLO, CHIAMATA A INTERROGAPRATICHE, xml: {0}", xmlRequest);
                    var response = ws.interrogaPratiche(request);

                    if (!response.esito)
                    {
                        var err = (Errore)response.Items.First();
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("INTERROGAZIONE DEL FASCICOLO, CHIAMATA A INTERROGAPRATICHE AAVENUTA CON SUCCESSO");

                    if (Convert.ToInt32(response.numPrat) == 0)
                    {
                        throw new Exception("NON E' STATO TROVATO ALCUN FASCICOLO CON IL CRITERIO SELEZIONATO");
                    }

                    if (response.Items.Length > 1)
                    {
                        Logs.Warn("ATTENZIONE, SONO STATI RESTITUITI PIU' FASCICOLI DALLA RICERCA");
                    }

                    //var retVal = (InterrogazionePraticheResponse)response.Items.First();
                    return (DettagliPratica)response.Items.First();
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INTERROGAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }

        }

        internal RegistriClassPratResponse GetClassifiche(RegistriClassPratRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.utente = _utente;
                    Logs.Info("Chiamata al web service getregistriclassprat (GETCLASSIFICHE)");

                    var response = ws.getRegistriClassPrat(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Items[0];
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("LETTURA DELLE CLASSIFICHE AVVENUTA CORRETTAMENTE");
                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELLE CLASSIFICHE, {0}", ex.Message), ex);
                }
            }
        }
    }
}