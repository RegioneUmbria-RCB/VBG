using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class Corrispondente
    {
        public string CodiceSoggetto { get; private set; }
        public string TipoPersona { get; private set; }
        public string CognomeDenominazione { get; private set; }
        public string Nome { get; private set; }
        public string CodiceFiscale { get; private set; }

        const string xpathBase = "/xapirest/getInterrogazioneProtocollo_Result/SEQ_Protocollo/Protocollo";
        const string Corrispondente_CodiceSoggetto = "Corrispondente_CodiceSoggetto";
        const string Corrispondente_TipoPersona = "Corrispondente_TipoPersona";
        const string Corrispondente_CognomeDenomina = "Corrispondente_CognomeDenomina";
        const string Corrispondente_Nome = "Corrispondente_Nome";
        const string Corrispondente_CodiceFiscale = "Corrispondente_CodiceFiscale";

        public Corrispondente(string xml, int idx)
        {
            var xmldoc = new XmlDocument();
            xmldoc.LoadXml(xml);

            CodiceSoggetto = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CodiceSoggetto, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CodiceSoggetto, idx)).Value);
            TipoPersona = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_TipoPersona, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_TipoPersona, idx)).Value);
            CognomeDenominazione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CognomeDenomina, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CognomeDenomina, idx)).Value);
            Nome = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_Nome, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_Nome, idx)).Value);
            CodiceFiscale = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CodiceFiscale, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Corrispondente_CodiceFiscale, idx)).Value);
        }
    }
}
