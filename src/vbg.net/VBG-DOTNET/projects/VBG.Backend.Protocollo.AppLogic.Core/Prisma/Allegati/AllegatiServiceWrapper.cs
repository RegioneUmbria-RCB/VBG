using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Allegati
{
    public class AllegatiServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        private readonly AllegatiClientServiceCreator _allegatiClientServiceCreator;
        private readonly string _username;

        public AllegatiServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentialsInfo, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._allegatiClientServiceCreator = new AllegatiClientServiceCreator(logs, bindingFactory, credentialsInfo, url);
            _username = credentialsInfo.Username;
        }

        public byte[] Download(string idDocumento, string idOggetto)
        {
            try
            {
                using (var ws = _allegatiClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();

                        _logs.Info($"RICHIESTA DI DOWNLOAD DELL'ALLEGATO CON CODICE OGGETTO {idOggetto}, DEL DOCUMENTO ID {idDocumento} CON L'UTENTE {_username}");
                        var response = ws.Service.downloadAttach(idDocumento, idOggetto, "", _username);

                        if (response.result != "0")
                        {
                            throw new Exception(response.errStr);
                        }

                        _logs.Info($"RICHIESTA DI DOWNLOAD DELL'ALLEGATO CON CODICE OGGETTO {idOggetto}, DEL DOCUMENTO ID {idDocumento} CON L'UTENTE {_username} TERMINATO");

                        return response.contentFile;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO CODICE {idOggetto} DEL DOCUMENTO CON ID {idDocumento}, {ex.Message}", ex);
            }
        }
    }
}
