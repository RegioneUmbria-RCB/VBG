
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace SIGePro.Data.Data.Rating
{
    [DataTable("FO_RATING_RESULT")]
    [Serializable]
    [DataContract]
    public class RatingResult : BaseDataClass
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

        [KeyField("IDENTIFIER", Size = 64, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 2)]
        public string Identifier { get; set; } = "";

        [KeyField("STARS", Type = DbType.Int32)]
        [DataMember]
        [XmlElement(Order = 3)]
        public int Stars { get; set; } = 0;

        [KeyField("FKIDCHOICE", Type = DbType.Int32)]
        [DataMember]
        [XmlElement(Order = 4)]
        public int FkIdChoice { get; set; } = 0;

        [DataMember]
        [DataField("RATINGCOMMENT", Size = 512, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 5)]
        public string RatingComment { get; set; } = "";
    }
}
