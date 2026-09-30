using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class UfficioDestinatario
    {
        public string Descrizione { get; private set; }

        const string xpathBase = "/xapirest/getInterrogazioneProtocollo_Result/SEQ_Protocollo/Protocollo";
        const string Ufficio_Destinatari = "Ufficio_Destinatari";

        public UfficioDestinatario(string xml, int idx)
        {
            var xmldoc = new XmlDocument();
            xmldoc.LoadXml(xml);

            Descrizione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Ufficio_Destinatari, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Ufficio_Destinatari, idx)).Value);
        }
    }
}
