
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace SIGePro.Data.Data.Rating
{
    [DataTable("FO_RATING_CHOICE")]
    [Serializable]
    [DataContract]
    public class RatingAnswer : BaseDataClass
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

        [KeyField("IDRATINGSUBQUESTION", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 2)]
        public int IdRatingSubQuestion { get; set; }

        [DataMember]
        [DataField("TESTO", Size = 256, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 3)]
        public string Testo { get; set; }

        [KeyField("ORDINE", Type = DbType.Int32)]
        [DataMember]
        [XmlElement(Order = 4)]
        public int Ordine { get; set; }
    }
}
