using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.ExtractMulti
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
                ServiceResponse = this.GetAllegatiSenzaDatiBinari()
            };
        }

        protected Output_FilesUD GetAllegatiSenzaDatiBinari()
        {
            if (this._response.allegati != null)
            {
                var resXml = StringSerializationExtensions.GetXmlDaStringaConEscapeHtmlEncoded(Encoding.Default.GetString(this._response.allegati[0].binaryData));
                resXml = resXml.Replace('\u00A0', ' ');

                return resXml.DeserializeXML<Output_FilesUD>();
            }
            return null;
        }


        protected Output_FilesUD GetAllegatiConDatiBinari()
        {
            if (this._response.allegati != null)
            {

                var retVal = new Output_FilesUD();

                for (var i = 0; i < this._response.allegati.Length; i++)
                {
                    string resXml = null;
                    if (i == 0)
                    {
                        resXml = StringSerializationExtensions.GetXmlDaStringaConEscapeHtmlEncoded(Encoding.Default.GetString(this._response.allegati[i].binaryData));
                        resXml = resXml.Replace('\u00A0', ' ');

                        retVal = resXml.DeserializeXML<Output_FilesUD>();
                    }
                    else
                    {
                        retVal.DatiFileEstratto[i - 1].Content = this._response.allegati[i].binaryData;
                    }

                }

                return retVal;
            }
            return null;
        }
    }
}
