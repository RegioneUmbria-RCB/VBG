using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario
{
    [XmlRoot(Namespace = "", ElementName = "_VETTORE_", IsNullable = false)]
    public class MittenteDestinatarioInternoXML
    {
        public MittenteDestinatarioInternoXML()
        {

        }

        [XmlElement(ElementName = "_DIM_", Order = 1)]
        public Dim Dimensione { get; set; }

        [XmlElement(ElementName = "_COL_0", Order = 2)]
        public string Denominazione { get; set; }
        [XmlElement(ElementName = "_COL_1", Order = 3)]
        public string Email { get; set; }
        [XmlElement(ElementName = "_COL_2", Order = 4)]
        public string UnitaOrganizzativa { get; set; }
        [XmlElement(ElementName = "_COL_3", Order = 5)]
        public string DescrizionePersonaDestinataria { get; set; }
        [XmlElement(ElementName = "_COL_4", Order = 6)]
        public string IndirizzoPostale { get; set; }
        [XmlElement(ElementName = "_COL_5", Order = 7)]
        public string CodiceFiscale { get; set; }

        public class Dim
        {
            [XmlElement(ElementName = "_N_COL_", Order = 1)]
            public int NumeroColonne { get; set; }
        }
    }
}
