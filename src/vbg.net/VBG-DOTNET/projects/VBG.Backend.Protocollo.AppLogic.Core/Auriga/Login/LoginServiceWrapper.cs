using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Login
{
    public class LoginServiceWrapper : ProxyServiceWrapper
    {
        private static class Constants
        {
            public const string Titolo = "LOGIN";
            public const string NameSpace = @"http://login.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSLogin";
            public const string RequestServiceName = "log";
        }

        public LoginServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs logs, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, logs, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        public ResponseInfo Login()
        {
            try
            {
                using (var ws = this.CreaWebService())
                {

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.LOGIN_REQUEST);
                    var xmlService = Utility.HtmlEncodeContent(this._serializer.Serialize(ProtocolloLogsConstants.LoginRequest, service));

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.LoginResponse, serviceResponse);

                    this.LogInfoResponseWS(responseXml);

                    var response = new ResponseInfoAdapter(serviceResponse).Adatta();
                    if (response.WsResult != "1")
                        throw new Exception(response.WsError);

                    this.LogSuccess();

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"{this._titolo} fallita: {ex.Message}", ex);
            }
        }

    }
}
