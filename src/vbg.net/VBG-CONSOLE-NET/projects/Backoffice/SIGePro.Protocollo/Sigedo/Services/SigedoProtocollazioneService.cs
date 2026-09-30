using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Sigedo.Proxies;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using System.IO;
using Microsoft.Web.Services2.Attachments;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Sigedo.Proxies.Protocollazione;
using Init.SIGePro.Protocollo.Sigedo.PresaInCarico;
using Init.SIGePro.Protocollo.Sigedo.AggiungiDocumenti;

namespace Init.SIGePro.Protocollo.Sigedo.Services
{
    public class SigedoProtocollazioneService
    {
        /*public static class Constants
        { 
            public const string CHIAVE_TIPODOCUMENTO = "TIPO_DOCUMENTO";
            public const string RIEPILOGO_DOMANDA = "RiepilogoDomanda";
        }*/


        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _endPointAddress;

        public SigedoProtocollazioneService(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _endPointAddress = endPointAddress;
        }

        private DOCAREAProto CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione Sigedo");
                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var ws = new DOCAREAProto { Url = _endPointAddress };

                _logs.Debug("Fine creazione del webservice SIGEDO");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE", ex.Message), ex);
            }
        }

        internal string Login(string codiceEnte, string username, string password)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a Login del web service, codice ente: {0}, username: {1}, password: {2}", codiceEnte, username, password);
                    var response = ws.Login(codiceEnte, username, password);
                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    if (String.IsNullOrEmpty(response.strDST))
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                    _logs.InfoFormat("Autenticazione al web service avvenuta correttamente, token restituito: {0}", response.strDST);

                    return response.strDST;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        internal void InserisciAllegati(List<ProtocolloAllegati> listAllegati, string token, string userName)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (listAllegati.Count == 0)
                        throw new Exception("NON SONO PRESENTI FILES ALLEGATI");

                    foreach (var all in listAllegati)
                    {
                        if (String.IsNullOrEmpty(all.NOMEFILE))
                            throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));

                        if (all.OGGETTO == null)
                            throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));

                        if (String.IsNullOrEmpty(all.MimeType))
                            throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));

                        string path = Path.Combine(_logs.Folder, all.NOMEFILE);
                        File.WriteAllBytes(path, all.OGGETTO);
                        _logs.InfoFormat("SALVATO IL FILE {0}, CODICE ALLEGATO {1}", all.CODICEOGGETTO, all.NOMEFILE);

                        var attachment = new Attachment(all.MimeType, path);

                        _logs.InfoFormat("Attachment del file: {0}, codice oggetto: {1}", all.NOMEFILE, all.CODICEOGGETTO);
                        ws.RequestSoapContext.Attachments.Add(attachment);
                        var response = ws.Inserimento(userName, token);

                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.lngErrNumber.ToString(), response.strErrString, all.NOMEFILE, all.CODICEOGGETTO));

                        _logs.InfoFormat("Inserimento del file: {0}, codice oggetto: {1} avvenuto correttamente, ID restituito: {2}", response.lngDocID, all.CODICEOGGETTO, response.lngDocID.ToString());
                        all.ID = response.lngDocID;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE, ERRORE: {0}", ex.Message), ex);
            }
        }

        private void AllegaSegnatura(DOCAREAProto ws)
        {
            string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
            Attachment attachment = new Attachment("text/xml", pathSegnatura);

            _logs.Info("Attachment del file segnatura.xml");
            ws.RequestSoapContext.Attachments.Add(attachment);

            File.Copy(pathSegnatura, Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaProtocollazioneXmlFileName));
        }

        private void AllegaSegnaturaSmistamentoAction(DOCAREAProto ws, SmistamentoActionSegnatura smistamentoSegnatura, string nomeFile)
        {
            _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, smistamentoSegnatura);
            string pathSegnaturaSmistamento = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);

            _logs.InfoFormat("SALVATAGGIO DEL FILE DI SEGNATURA PRESA IN CARICO {0}", ProtocolloLogsConstants.SegnaturaXmlFileName);

            var attachment = new Attachment("text/xml", pathSegnaturaSmistamento);
            ws.RequestSoapContext.Attachments.Add(attachment);

            File.Copy(pathSegnaturaSmistamento, Path.Combine(_logs.Folder, nomeFile));
        }

        internal void PresaInCarico(string username, string token, SmistamentoActionSegnatura smistamentoSegnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    AllegaSegnaturaSmistamentoAction(ws, smistamentoSegnatura, ProtocolloLogsConstants.SegnaturaPresaIncaricoXmlFileName);
                    var response = ws.SmistamentoAction(username, token);

                    string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    File.Delete(pathSegnatura);
                    
                    if (response != null && response.lngErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    _logs.Info("PRESA IN CARICO AVVENUTA CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA PRESA IN CARICO DAL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message));
            }
        }

        internal void Eseguito(string username, string token, SmistamentoActionSegnatura smistamentoSegnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    AllegaSegnaturaSmistamentoAction(ws, smistamentoSegnatura, ProtocolloLogsConstants.SegnaturaEseguitoXmlFileName);
                    var response = ws.SmistamentoAction(username, token);

                    string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    File.Delete(pathSegnatura);

                    if (response != null && response.lngErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    _logs.Info("ESEGUITO AVVENUTO CON SUCCESSO");
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE L'ESEGUITO DAL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message));
            }
        }

        internal ProtocollazioneRet Protocollazione(string userName, string token)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    AllegaSegnatura(ws);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", token, userName, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    var response = ws.Protocollazione(userName, token);

                    string pathSegnatura = Path.Combine(_logs.Folder, ProtocolloLogsConstants.SegnaturaXmlFileName);
                    File.Delete(pathSegnatura);

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response != null && response.lngErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG);
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE {0}", ex.Message), ex);
            }
        }

        internal void AggiungiAllegatiaProtocollo(string token, string usernName, SegnaturaAggiungiDocumenti segnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);
                    AllegaSegnatura(ws);

                    _logs.InfoFormat("CHIAMATA A METODO AGGIUNGIALLEGATO A PROTOCOLLO NUMERO {0} ANNO {1}, ID ALLEGATI {2}", segnatura.Intestazione.Identificatore.NumeroProtocollo, segnatura.Intestazione.Identificatore.AnnoProtocollo, String.Join("|", segnatura.Descrizione.Allegati.Select(x => x.id).ToArray()));
                    var response = ws.AggiungiAllegato(usernName, token);

                    if (response != null && response.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO: {0}, DESCRIZIONE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    _logs.InfoFormat("CHIAMATA A METODO AGGIUNGIALLEGATO AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO {0} ANNO {1}, ID ALLEGATI {2}", segnatura.Intestazione.Identificatore.NumeroProtocollo, segnatura.Intestazione.Identificatore.AnnoProtocollo, String.Join("|", segnatura.Descrizione.Allegati.Select(x => x.id).ToArray()));
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE PER L'AGGIUNTA DI ALLEGATI AL PROTOCOLLO, ERRORE {0}", ex.Message), ex);
            }
        }
    }
}
