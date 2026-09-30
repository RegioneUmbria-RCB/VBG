using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    public class AuthenticationServiceWrapper
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _password;
        private readonly string _codiceEnte;

        public AuthenticationServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string username, string password, string codiceEnte)
        {
            _logs = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, new CredentialsInfo(username, password, codiceEnte, ""), url);
            _username = username;
            _password = password;
            _codiceEnte = codiceEnte;
        }

        public string Login()
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        _logs.InfoFormat("CHIAMATA A LOGIN DEL WEB SERVICE DOCAREA, CODICE ENTE: {0}, USERNAME: {1}, PASSWORD: {2}", _codiceEnte, _username, _password);
                        var response = ws.Service.login(_codiceEnte, _username, _password);
                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                        if (String.IsNullOrEmpty(response.strDST))
                            throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                        _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE AVVENUTA CORRETTAMENTE, TOKEN RESTITUITO: {0}", response.strDST);

                        return response.strDST;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE {0}", ex.Message), ex);
            }
        }
    }
}
