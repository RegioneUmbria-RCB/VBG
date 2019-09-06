using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.SIGePro.Protocollo.Urbi.LeggiProtocollo
{
    public class Allegato
    {
        public string NomeFile { get; private set; }
        public string Estensione { get; private set; }
        public string Dimensione { get; private set; }
        public string Descrizione { get; private set; }
        public string IdTestata { get; private set; }
        public string IdVersione { get; private set; }
        public string PrgAllegato { get; private set; }
        public string Classificazione { get; private set; }

        const string xpathBase = "/xapirest/getInterrogazioneProtocollo_Result/SEQ_Protocollo/Protocollo";
        const string Documento_NomeFile = "Documento_NomeFile";
        const string Documento_Estensione_File = "Documento_Estensione_File";
        const string Documento_Dimensione_File = "Documento_Dimensione_File";
        const string Documento_Classificazione = "Documento_Classificazione";
        const string Documento_Descrizione_File = "Documento_Descrizione_File";
        const string Documento_id_testata = "Documento_id_testata";
        const string Documento_id_versione = "Documento_id_versione";
        const string Documento_PrgAllegato = "Documento_PrgAllegato";

        public Allegato(string xml, int idx)
        {
            var xmldoc = new XmlDocument();
            xmldoc.LoadXml(xml);

            NomeFile = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_NomeFile, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_NomeFile, idx)).Value);
            Estensione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Estensione_File, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Estensione_File, idx)).Value);
            Dimensione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Dimensione_File, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Dimensione_File, idx)).Value);
            Classificazione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Classificazione, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Classificazione, idx)).Value);
            Descrizione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Descrizione_File, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_Descrizione_File, idx)).Value);
            IdTestata = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_id_testata, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_id_testata, idx)).Value);
            IdVersione = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_id_versione, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_id_versione, idx)).Value);
            PrgAllegato = xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_PrgAllegato, idx)) == null ? "" : Utility.FormattaValoriDaDeserializzare(xmldoc.SelectSingleNode(String.Format("{0}/{1}{2}/text()", xpathBase, Documento_PrgAllegato, idx)).Value);
        }
    }
}
