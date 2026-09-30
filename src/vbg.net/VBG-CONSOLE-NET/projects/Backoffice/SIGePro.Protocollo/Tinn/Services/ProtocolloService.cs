using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloTinnServiceProxy;
using System.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System.IO;

namespace Init.SIGePro.Protocollo.Tinn.Services
{
    public class ProtocolloService
    {
        string _url;
        string _proxy;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _token;
        string _username;

        public ProtocolloService(string url, string proxy, string username, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _url = url;
            _proxy = proxy;
            _logs = logs;
            _serializer = serializer;
            _username = username;
        }

        public DOCAREAProtoClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);

                var binding = new BasicHttpBinding("tinnHttpBinding");

                if (!String.IsNullOrEmpty(_proxy))
                {
                    binding.UseDefaultWebProxy = false;
                    binding.ProxyAddress = new Uri(_proxy);
                }

                var client = new DOCAREAProtoClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice TINN");

                return client;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE", ex);
            }
        }

        internal void Login(string codiceEnte, string password)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("Chiamata a Login del web service, codice ente: {0}, username: {1}, password: {2}", codiceEnte, _username, password);
                    var response = ws.Login(codiceEnte, _username, password);
                    if (response.IngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.IngErrNumber.ToString(), response.strErrString));

                    if (String.IsNullOrEmpty(response.strDST))
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                    _logs.InfoFormat("Autenticazione al web service avvenuta correttamente, token restituito: {0}", response.strDST);

                    _token = response.strDST;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE", ex);
            }
        }

        internal void InserisciAllegato(ProtocolloAllegati allegato)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    if (allegato.OGGETTO == null)
                        throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", allegato.CODICEOGGETTO, allegato.NOMEFILE));

                    File.WriteAllBytes(Path.Combine(_logs.Folder, allegato.NOMEFILE), allegato.OGGETTO);

                    var response = ws.Inserimento(_username, _token, allegato.OGGETTO);

                    if (response.IngErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD INSERIMENTO, ERRORE CODICE:{0}, DESCRIZIONE: {1}, FILE: {2}, CODICE OGGETTO: {3}", response.IngErrNumber.ToString(), response.strErrString, allegato.NOMEFILE, allegato.CODICEOGGETTO));

                    _logs.InfoFormat("INSERIMENTO DEL FILE: {0}, CODICE OGGETTO: {1} AVVENUTO CORRETTAMENTE, ID RESTITUITO: {2}", allegato.NOMEFILE, allegato.CODICEOGGETTO, response.IngDocID.ToString());
                    allegato.ID = response.IngDocID;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE", ex);
            }
        }

        internal TRispostaProtocollazione Protocollazione(byte[] segnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", _token, _username, ProtocolloLogsConstants.SegnaturaXmlFileName);

                    var response = ws.Protocollazione(_username, _token, segnatura);
                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    if (response.IngErrNumber != 0 || !String.IsNullOrEmpty(response.strErrString))
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.IngErrNumber.ToString(), response.strErrString));

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.IngNumPG.ToString(), response.StrDataPG, response.IngAnnoPG.ToString());
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE", ex);
            }
        }
    }
}
