using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder.Request;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder
{
    public class GetMetadataFolderServiceWrapper : ProxyServiceWrapper
    {
        private static class Constants
        {
            public const string Titolo = "LEGGI FASCICOLO";
            public const string NameSpace = @"http://getmetadatafolder.webservices.repository2.auriga.eng.it";
            public const string ServiceName = "WSGetMetadataFolder";
            public const string RequestServiceName = "get";
        }

        public GetMetadataFolderServiceWrapper(ParametriRegoleInfo parametri, ProtocolloSerializer serializer, ProtocolloLogs log, ProxyRequestInfo request, IBindingFactory bindingFactory)
            : base(parametri, serializer, log, request, bindingFactory, Constants.Titolo, Constants.ServiceName)
        {
        }

        public ResponseInfo LeggiFascicoloDaID(string idFascicolo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var request = new EstremiXIdentificazioneFolderType
                    {
                        Items = new object[] { idFascicolo },
                        ItemsElementName = new ItemsChoiceType[] { ItemsChoiceType.IdFolder }
                    };

                    var requestXml = this._serializer.Serialize("letturaFascicoloRequest.xml", request);

                    this._request.xml = requestXml;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.LETTURA_FASCICOLO_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.LeggiFascicoloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.LeggiFascicoloRequestFileName, serviceResponse);

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

        public ResponseInfo LeggiFascicoloDaPath(string libreria, string pathNome)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var request = new EstremiXIdentificazioneFolderType
                    {
                        Items = new object[] {
                            new OggDiTabDiSistemaType
                            {
                                Item = libreria,
                                ItemElementName = ItemChoiceType.Decodifica_Nome
                            },
                            pathNome
                        },
                        ItemsElementName = new ItemsChoiceType[]
                        {
                            ItemsChoiceType.Libreria,
                            ItemsChoiceType.Path_Nome
                        }
                    };

                    var requestXml = Utility.HtmlEncodeContent(this._serializer.Serialize("letturaFascicoloRequest.xml", request));

                    this._request.xml = requestXml;
                    this._request.hash = this.getHashSHA1();

                    var service = this.CreateServiceRequest(Constants.NameSpace, Constants.RequestServiceName, TipoOperazione.LETTURA_FASCICOLO_REQUEST);
                    var xmlService = this._serializer.Serialize(ProtocolloLogsConstants.LeggiFascicoloRequestFileName, service);

                    this.LogInfoRequestWS(xmlService);

                    var serviceResponse = ws.AurigaProxy(service);
                    var responseXml = this._serializer.Serialize(ProtocolloLogsConstants.LeggiFascicoloRequestFileName, serviceResponse);

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
