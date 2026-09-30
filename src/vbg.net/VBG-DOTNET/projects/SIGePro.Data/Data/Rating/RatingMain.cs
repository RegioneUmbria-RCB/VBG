
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace SIGePro.Data.Data.Rating
{
    [DataTable("FO_RATING_MAIN")]
    [Serializable]
    [DataContract]
    public class RatingMain : BaseDataClass
    {
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string IdComune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 1)]
        public int? Id { get; set; }

        [DataMember]
        [DataField("TIPO", Size = 64, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 2)]
        public string Tipo { get; set; }

        [DataMember]
        [DataField("TESTO", Size = 256, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 3)]
        public string Testo { get; set; }
    }
}
