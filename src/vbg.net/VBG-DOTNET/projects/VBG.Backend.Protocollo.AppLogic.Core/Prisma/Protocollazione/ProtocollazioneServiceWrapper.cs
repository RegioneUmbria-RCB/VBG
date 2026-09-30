using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloPrismaService;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _token;

        public ProtocollazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, CredentialsInfo credentials)
        {
            _logs = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, credentials, url);
            _username = credentials.Username;
            _token = credentials.Token;
        }

        public long InserimentoAllegato(ProtocolloAllegati all)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        if (String.IsNullOrEmpty(all.NOMEFILE))
                        {
                            throw new Exception(String.Format("IL NOME FILE DELL'ALLEGATO CON CODICE OGGETTO: {0}, NON E' VALORIZZATO", all.CODICEOGGETTO));
                        }

                        if ((all.OGGETTO?.Length ?? 0) == 0)
                        {
                            throw new Exception(String.Format("IL BUFFER DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1} E' NULL", all.CODICEOGGETTO, all.NOMEFILE));
                        }

                        _logs.InfoFormat($"Dimensione dell'allegato {all.NOMEFILE} ({all.CODICEOGGETTO}): {all.OGGETTO.Length}");

                        if (String.IsNullOrEmpty(all.MimeType))
                        {
                            throw new Exception(String.Format("IL CONTENT TYPE DELL'ALLEGATO CON CODICE OGGETTO: {0} E NOME FILE: {1}, NON E' VALORIZZATO", all.CODICEOGGETTO, all.NOMEFILE));
                        }

                        _logs.InfoFormat("CHIAMATA A INSERIMENTO DEL FILE {0}, CODICE ALLEGATO {1}", all.NOMEFILE, all.CODICEOGGETTO);
                        var response = ws.Service.inserimento(_username, _token, all.OGGETTO);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception(String.Format("ERRORE CODICE:{0}, DESCRIZIONE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                        }

                        _logs.InfoFormat("INSERIMENTO DEL FILE: {0}, CODICE OGGETTO: {1} AVVENUTO CORRETTAMENTE, ID RESTITUITO: {2}", all.NOMEFILE, all.CODICEOGGETTO, response.lngDocID.ToString());
                        return response.lngDocID;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'UPLOAD DEL FILE {0}, {1}", all.NOMEFILE, ex.Message), ex);
            }
        }

        public ProtocollazioneRet Protocolla(DocAreaSegnaturaInput request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        var requestXml = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, request);
                        var s = _serializer.SerializeToStream<DocAreaSegnaturaInput>(request);

                        _logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE token: {0}, username: {1}, dati protocollo: {2}", _token, _username, ProtocolloLogsConstants.SegnaturaXmlFileName);
                        var response = ws.Service.protocollazione(_username, _token, s.ToArray());
                        _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                        if (response.lngErrNumber != 0)
                        {
                            throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));
                        }

                        _logs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO, numero protocollo: {0}, data protocollo: {1}, anno protocollo: {2}", response.lngNumPG.ToString(), response.strDataPG, response.lngAnnoPG.ToString());
                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA CHIAMATA AL WEB SERVICE DI PROTOCOLLAZIONE {0}", ex.Message), ex);
            }
        }
    }
}
