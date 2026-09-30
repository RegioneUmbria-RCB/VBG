using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace SIGePro.Data.Data.Rating
{
    [DataTable("FO_RATING_SUB_QUESTION")]
    [Serializable]
    [DataContract]
    public class RatingSubSection : BaseDataClass
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
        [DataField("IDRATINGMAIN", Type = DbType.Decimal)]
        [XmlElement(Order = 2)]
        public int IdRatingMain { get; set; }

        [DataMember]
        [DataField("ISPOSITIVE", Type = DbType.Int32)]
        [XmlElement(Order = 3)]
        public int IsPositive { get; set; }

        public bool IsPositiveBool
        {
            get { return this.IsPositive == 1; }
        }

        [DataMember]
        [DataField("TESTO", Size = 256, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 4)]
        public string Testo { get; set; }
    }
}
