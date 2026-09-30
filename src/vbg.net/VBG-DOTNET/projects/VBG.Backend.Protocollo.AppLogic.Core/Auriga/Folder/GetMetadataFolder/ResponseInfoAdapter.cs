using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder.Response;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.GetMetadataFolder
{
    public class ResponseInfoAdapter : ProxyResponseInfoAdapter
    {
        public ResponseInfoAdapter(AurigaProxyResponseType response) : base(response)
        {

        }

        public ResponseInfo Adatta()
        {
            return new ResponseInfo
            {
                WarningMessage = base.GetWarningMessage(),
                WsError = base.GetWsError(),
                WsResult = base.GetResult(),
                ServiceResponse = this.GetDatiFolder()
            };
        }

        protected DatiFolder GetDatiFolder()
        {
            if (this._response.allegati != null)
            {

                var resXml = StringSerializationExtensions.GetXmlDaStringaConEscapeHtmlEncoded(Encoding.Default.GetString(this._response.allegati[0].binaryData));
                resXml = resXml.Replace('\u00A0', ' ');

                return resXml.DeserializeXML<DatiFolder>();
            }

            return null;
        }
    }
}
