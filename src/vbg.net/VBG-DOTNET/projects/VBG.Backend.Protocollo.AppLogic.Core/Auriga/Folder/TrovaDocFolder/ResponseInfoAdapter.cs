
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.Exceptions;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.TrovaDocFolder
{
    public class ResponseInfoAdapter : ProxyResponseInfoAdapter
    {
        public ResponseInfoAdapter(AurigaProxyResponseType response) : base(response)
        {
        }

        public ResponseInfo Adatta()
        {
            var response = new ResponseInfo
            {
                WarningMessage = base.GetWarningMessage(),
                WsError = base.GetWsError(),
                WsResult = base.GetResult()
            };

            try
            {
                response.ServiceResponse = this.GetServiceResponse();
            }
            catch (ResponseErrorException ex)
            {
                response.WsError = ex.Messaggio;
            }


            return response;
        }

        protected Lista GetServiceResponse()
        {
            if (this._response.allegati != null)
            {
                var resXml = StringSerializationExtensions.GetXmlDaStringaConEscapeHtmlEncoded(Encoding.Default.GetString(this._response.allegati[0].binaryData));
                resXml = resXml.Replace('\u00A0', ' ');

                if (resXml.StartsWith("Errore"))
                {
                    throw new ResponseErrorException(resXml);
                }

                return resXml.DeserializeXML<Lista>();
            }
            return null;
        }
    }
}
