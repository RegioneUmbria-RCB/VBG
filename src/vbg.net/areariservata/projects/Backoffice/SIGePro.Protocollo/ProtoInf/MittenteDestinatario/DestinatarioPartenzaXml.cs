using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.MittenteDestinatario
{
    [XmlRoot(Namespace = "", ElementName = "_MATRICE_", IsNullable = false)]
    public class DestinatarioPartenzaXML
    {
        [XmlElement(ElementName = "_DIM_", Order = 1)]
        public Dim Dimensione { get; set; }

        [XmlElement(ElementName = "_RIGA_IDX", Order = 2)]
        public Riga[] Righe { get; set; }

        public class Dim
        {
            [XmlElement(ElementName = "_N_COL_", Order = 1)]
            public int NumeroColonne = 7;
            [XmlElement(ElementName = "_N_RIGHE_", Order = 2)]
            public int NumeroRighe { get; set; }
        }

        public class Riga
        {
            [XmlAttribute()]
            public int Index { get; set; }
            [XmlElement(ElementName = "_COL_0", Order = 1)]
            public string Flusso { get; set; }
            [XmlElement(ElementName = "_COL_1", Order = 2)]
            public string Denominazione { get; set; }
            [XmlElement(ElementName = "_COL_2", Order = 3)]
            public string Email { get; set; }
            [XmlElement(ElementName = "_COL_3", Order = 4)]
            public string UnitaOrganizzativa { get; set; }
            [XmlElement(ElementName = "_COL_4", Order = 5)]
            public string DescrizionePersonaDestinataria { get; set; }
            [XmlElement(ElementName = "_COL_5", Order = 6)]
            public string IndirizzoPostale { get; set; }
            [XmlElement(ElementName = "_COL_6", Order = 7)]
            public string CodiceFiscale { get; set; }
        }
    }
}
