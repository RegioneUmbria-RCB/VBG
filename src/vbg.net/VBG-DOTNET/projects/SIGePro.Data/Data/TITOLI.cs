using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("TITOLI")]
    [Serializable]
    [DataContract]
    public class Titoli : BaseDataClass
    {

        #region Key Fields

        [useSequence]
        [KeyField("CODICETITOLO", Type = DbType.Decimal)]
        [XmlElement(Order = 0)]
        [DataMember]
        public string CODICETITOLO { get; set; }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [XmlElement(Order = 1)]
        [DataMember]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private string titolo = null;
        [DataField("TITOLO", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 2)]
        [DataMember]
        public string TITOLO
        {
            get { return this.titolo; }
            set { this.titolo = value; }
        }

    }
}