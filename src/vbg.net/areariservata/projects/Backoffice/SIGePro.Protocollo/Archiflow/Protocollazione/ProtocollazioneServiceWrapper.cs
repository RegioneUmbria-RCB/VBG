using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloArchiFlowServiceReference;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Archiflow.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;
        Login _credenziali;

        public ProtocollazioneServiceWrapper(string url, Login credenziali, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _url = url;
            _credenziali = credenziali;
        }

        private IwcfInsertClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione ARCHIFLOW");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("archiFlowHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_ARCHIFLOW NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new IwcfInsertClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di protocollazione ARCHIFLOW");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        private string Login()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("RICHIESTA DI AUTENTICAZIONE, USERNAME: {0}, PASSWORD: {1}, CODICE ENTE: {2}", _credenziali.Username, _credenziali.Password, _credenziali.CodEnte);
                    var response = ws.Login(_credenziali);

                    if (response.error_number != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.error_number, response.error_description));

                    _logs.InfoFormat("AUTENTICAZIONE AVVENUTA CORRETTAMENTE, TOKEN: {0}", response.Token);

                    return response.Token;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public SuapProtoResponse ProtocollazioneArrivo(SuapInsertProto metadati)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var token = Login();

                    _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, metadati);
                    _logs.Info("RICHIESTA DI PROTOCOLLAZIONE");
                    var response = ws.SuapGetProto(metadati, token);

                    if (response.ErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.ErrNumber, response.ErrDescription));

                    _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE, NUMERO: {0}, DATA: {1}", response.Numeroprotocollo.ToString(), response.dataProtocollo);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InserimentoDocumentoPrincipale(Guid guidCard, ProtocolloAllegati doc)
        {
            try
            {
                using(var ws = CreaWebService())
                {
                    var token = Login();

                    _logs.InfoFormat("INSERIMENTO DEL DOCUMENTO PRINCIPALE, CODICE OGGETTO: {0}, NOME: {1}", doc.CODICEOGGETTO, doc.NOMEFILE);
                    var response = ws.SuapInsertDoc(guidCard, token, doc.OGGETTO, doc.Extension);

                    if (response.ErrNumber != 0)
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.ErrNumber, response.ErrDescription));

                    _logs.InfoFormat("INSERIMENTO DEL DOCUMENTO PRINCIPALE AVVENUTO CORRETTAMENTE, CODICE OGGETTO: {0}, NOME: {1}", doc.CODICEOGGETTO, doc.NOMEFILE);
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO PRINCIPALE, ERRORE: {0}", ex.Message), ex);
            }
        }

        public void InserimentoAllegati(Guid guidCard, oAttachmentCard[] docs)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var token = Login();
                    int numeroErrori = 0;

                    foreach (var doc in docs)
                    {
                        _logs.Info($"INSERIMENTO ALLEGATO {doc.Filename}");
                        var response = ws.InsertAttchmentEx(guidCard, token, docs);

                        if (response.ErrNumber != 0)
                        {
                            _logs.Error($"ERRORE RESTITUITO DAL WEB SERVICE, CODICE ERRORE: {response.ErrNumber}, DESCRIZIONE ERRORE: {response.ErrDescription}");
                            numeroErrori++;
                        }
                    }

                    if (numeroErrori > 0)
                    {
                        _logs.Warn($"INSERIMENTO ALLEGATI AVVENUTO CON WARNINGS, {numeroErrori} FILE NON SONO STATI INSERITI");
                    }
                    else
                    {
                        _logs.InfoFormat("INSERIMENTO DEGLI ALLEGATI AVVENUTO CORRETTAMENTE");
                    }
                }
            }
            catch (Exception ex)
            {
                _logs.WarnFormat(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DEGLI ALLEGATI, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
