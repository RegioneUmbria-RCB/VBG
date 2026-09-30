using System.Xml;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Login
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

                WsResult = this.GetResult(),
                WarningMessage = this.GetWarningMessage(),
                WsError = this.GetWsError(),
                TokenConnessione = this.GetToken()
            };
        }



        protected TokenConnessione GetToken()
        {
            var retVal = new TokenConnessione();
            retVal.Token = new TokenConnessioneElement();

            if (this._response.allegati != null)
            {
                var myXML = new XmlDocument();
                var ms = new MemoryStream(this._response.allegati[0].binaryData);
                myXML.Load(ms);

                var list = myXML.GetElementsByTagName("TokenConnessione");
                if (list != null)
                {
                    var el = list[0];
                    foreach (XmlAttribute attr in el.Attributes)
                    {
                        switch (attr.Name)
                        {
                            case "DesUser":
                                {
                                    retVal.Token.DesUser = attr.Value;
                                    break;
                                }
                            case "IdDominio":
                                {
                                    retVal.Token.IdDominio = attr.Value;
                                    break;
                                }
                            default:
                                break;
                        }
                    }

                    retVal.Token.Valore = el.InnerText;
                }
            }

            return retVal;
        }
    }
}
