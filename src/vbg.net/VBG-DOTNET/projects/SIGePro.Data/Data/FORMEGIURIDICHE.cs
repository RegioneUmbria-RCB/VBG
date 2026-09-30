using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("FORMEGIURIDICHE")]
    [Serializable]
    [DataContract]
    public class FormeGiuridiche : BaseDataClass
    {

        #region Key Fields

        private string codiceformagiuridica = null;
        [useSequence]
        [KeyField("CODICEFORMAGIURIDICA", Type = DbType.Decimal)]
        [XmlElement(Order = 0)]
        [DataMember]
        public string CODICEFORMAGIURIDICA
        {
            get { return this.codiceformagiuridica; }
            set { this.codiceformagiuridica = value; }
        }

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

        private string formagiuridica = null;
        [DataField("FORMAGIURIDICA", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 2)]
        [DataMember]
        public string FORMAGIURIDICA
        {
            get { return this.formagiuridica; }
            set { this.formagiuridica = value; }
        }

        private string codicecciaa = null;
        [DataField("CODICECCIAA", Size = 2, Type = DbType.String, CaseSensitive = false)]
        [XmlElement(Order = 3)]
        [DataMember]
        public string CODICECCIAA
        {
            get { return this.codicecciaa; }
            set { this.codicecciaa = value; }
        }

    }
}