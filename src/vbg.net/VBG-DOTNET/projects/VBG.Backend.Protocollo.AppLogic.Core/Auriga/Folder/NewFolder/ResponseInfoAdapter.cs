using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.NewFolder
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
                IdFolder = this.GetIdFolder()
            };
        }

        protected string GetIdFolder()
        {
            if (this._response.allegati != null)
            {

                var resXml = StringSerializationExtensions.GetXmlDaStringaConEscape(Encoding.Default.GetString(this._response.allegati[0].binaryData));
                resXml = resXml.Replace('\u00A0', ' ');

                var r = resXml.DeserializeXML<ServiceResponseInfo>();
                if (r != null)
                    return r.IdFolder;
            }

            return null;
        }
    }
}
