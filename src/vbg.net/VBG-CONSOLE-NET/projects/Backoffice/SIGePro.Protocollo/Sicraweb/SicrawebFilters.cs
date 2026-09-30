/*using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Web.Services2;
using System.Xml;

namespace Init.SIGePro.Protocollo.Sicraweb
{
    public class SicrawebFilters : SoapOutputFilter
    {
        public override void ProcessMessage(SoapEnvelope envelope)
        {
            RemoveTag(envelope.DocumentElement, "soap:Header");
        }
        private void RemoveTag(System.Xml.XmlNode XE, string TagName)
        {
            foreach (XmlNode N in XE)
            {
                if (N.Name == TagName) XE.RemoveChild(N);
                else if (N.ChildNodes.Count > 0) RemoveTag(N, TagName);

            }
        }
    }
}
*/