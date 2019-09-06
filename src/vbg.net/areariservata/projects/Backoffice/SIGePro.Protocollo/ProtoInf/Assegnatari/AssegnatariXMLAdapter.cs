using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Serialize;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Linq;

namespace Init.SIGePro.Protocollo.ProtoInf.Assegnatari
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

            var xml = serializer.Serialize("AssegnatariXML.xml", assegnatariXml, Validation.ProtocolloValidation.TipiValidazione.PROTOCOLLOXML_PROTOINF);
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
