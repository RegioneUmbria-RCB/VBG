using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.ExtractMulti
{
    public class ExtractMultiServiceWrapper : ProxyServiceWrapper
    {
        private static class Constants
        {
            public const string Titolo = "ESTRAZIONE ALLEGATI";
            public const string NameSpace = @"http://extractmulti.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSExtractMulti";
            public const string RequestServiceName = "ext";
            public const EstremiRegNumTypeCategoriaReg registroDefault = EstremiRegNumTypeCategoriaReg.PG;
        }

        public ExtractMultiServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        public ResponseInfo EstraiAllegati(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var estraiAllegatiRequest = new EstremiXIdentificazioneUDAllegati
                    {
                        Item = !String.IsNullOrEmpty(idProtocollo) ? (object)idProtocollo : new EstremiRegNumType
                        {
                            AnnoReg = annoProtocollo,
                            NumReg = numeroProtocollo,
                            CategoriaReg = Constants.registroDefault,
                            SiglaReg = null
                        },
                    };

                    var estraiAllegatiRequestXML = Utility.HtmlEncodeContent(this._serializer.Serialize("estraiAllegatiRequest.xml", estraiAllegatiRequest));

                    this._request.xml = estraiAllegatiRequestXML;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.ESTRAI_ALLEGATI_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.AllegatoRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.AllegatoResponseFileName, serviceResponse);

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
