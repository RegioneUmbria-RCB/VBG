using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.Iride2.CreaCopie
{
    [XmlRoot(Namespace = "", ElementName = "COllegaDocumentoIn", IsNullable = false)]
    public class CollegaDocumentoIn
    {
        [XmlElement(ElementName = "IdDocCollegante", Order = 1)]
        public int IdDocCollegante { get; set; }

        [XmlElement(ElementName = "IdDocCollegato", Order = 2)]
        public int IdDocCollegato { get; set; }

        [XmlElement(ElementName = "TipoCollegamento", Order = 3)]
        public string TipoCollegamento { get; set; }

        public CollegaDocumentoIn()
        {

        }
    }
}
