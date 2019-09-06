using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Protocollo.ProtoInf.Allegati
{
    [XmlRoot(Namespace = "", ElementName = "_MATRICE_", IsNullable = false)]
    public class AllegatiXML
    {
        [XmlElement(ElementName = "_DIM_", Order = 1)]
        public Dim Dimensione { get; set; }

        [XmlElement(ElementName = "_RIGA_IDX", Order = 2)]
        public Riga[] Righe { get; set; }

        public AllegatiXML()
        {

        }

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
            public string TipoAllegato { get; set; }
            [XmlElement(ElementName = "_COL_1", Order = 2)]
            public string Percorso { get; set; }
            [XmlElement(ElementName = "_COL_2", Order = 3)]
            public string NomeFile { get; set; }
        }

    }
}
