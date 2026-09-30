using System.Text;
using System.Xml;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.ServiceProxy;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga
{
    public class ProxyResponseInfoAdapter
    {
        protected AurigaProxyResponseType _response;

        public ProxyResponseInfoAdapter(AurigaProxyResponseType response)
        {
            this._response = response;
        }

        protected string GetWsError()
        {
            var xml = Encoding.UTF8.GetString(Convert.FromBase64String(this._response.xml));

            var xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(xml);

            var xpath = "BaseOutput_WS/WSError";
            var node = xmlDoc.SelectSingleNode(xpath);

            if (node != null)
                return node.InnerText;

            return null;
        }

        protected string GetResult()
        {
            var xml = Encoding.UTF8.GetString(Convert.FromBase64String(this._response.xml));

            var xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(xml);

            var xpath = "BaseOutput_WS/WSResult";
            var node = xmlDoc.SelectSingleNode(xpath);

            if (node != null)
                return node.InnerText;

            return null;
        }

        protected string GetWarningMessage()
        {
            var xml = Encoding.UTF8.GetString(Convert.FromBase64String(this._response.xml));

            var xmlDoc = new XmlDocument();
            xmlDoc.LoadXml(xml);

            var xpath = "BaseOutput_WS/WarningMessage";
            var node = xmlDoc.SelectSingleNode(xpath);

            if (node != null)
                return node.InnerText;

            return null;
        }

        protected string RemoveXMLDeclaration(string xml)
        {
            if (!string.IsNullOrEmpty(xml) && xml.IndexOf("?>") > -1)
            {
                return xml.Substring(xml.IndexOf("?>") + 2);
            }
            return xml;
        }
    }
}
