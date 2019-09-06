using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZECOLLEGATE")]
    [Serializable]
    public class IstanzaCollegata : BaseDataClass
    {
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 1)]
        public string IdComune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [XmlElement(Order = 2)]
        public int? Id { get; set; }

        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        [XmlElement(Order = 3)]
        public int CodiceIstanza { get; set; }

        [DataField("PROGRESSIVO", Type = DbType.Decimal)]
        [XmlElement(Order = 4)]
        public int Progressivo { get; set; }

        [DataField("ORDINE", Type = DbType.Decimal)]
        [XmlElement(Order = 5)]
        public int Ordine { get; set; }

        [DataField("CODICEISTANZACOLLEGATA", Type = DbType.Decimal)]
        [XmlElement(Order = 6)]
        public int CodiceIstanzaCollegata { get; set; }
    }
}
