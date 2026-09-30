using PersonalLib2.Data;
using System.Xml.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.Assegnatari
{
    public class AssegnatariXMLAdapter
    {
        private class ConstantsTipoAssegnatario
        {
            public const string Principale = "P";
            public const string Conoscenza = "C";
        }

        public AssegnatariXMLAdapter()
        {

        }

        public string Adatta(RequestInfo info, ProtocolloSerializer serializer)
        {
            string[] ruoloAssegnatari = info.DatiConfProtocollo.Ruolo.Split(';');

            if (info.Metadati.Flusso != ProtocolloConstants.COD_ARRIVO)
            {
                return "";
            }

            var assegnatariXml = new AssegnatariXML
            {
                Dimensione = new AssegnatariXML.Dim
                {
                    NumeroColonne = 2,
                    NumeroRighe = ruoloAssegnatari.Length
                },
                Righe = ruoloAssegnatari.Select((x, i) => new AssegnatariXML.Riga { Assegnatario = x, Index = i, TipoAssegnatario = (i == 0 ? "P" : "C") }).ToArray()
            };

            var xml = serializer.Serialize("AssegnatariXML.xml", assegnatariXml, VBG.Backend.Protocollo.AppLogic.Shared.Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
            var doc = XDocument.Parse(xml);

            foreach (var element in doc.Descendants())
            {
                if (element.Name.LocalName.StartsWith("_RIGA_IDX"))
                {
                    var indice = element.Attribute("Index");
                    element.Name = $"_RIGA_{indice.Value}";
                    element.Attribute("Index").Remove();
                }
            }

            return doc.ToString();
        }
    }
}
