using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("VW_PROVINCE")]
    [Serializable]
    [DataContract]
    public class VwProvince : BaseDataClass
    {
        #region Key Fields

        private string siglaprovincia = null;
        [KeyField("SIGLAPROVINCIA", Size = 2, Type = DbType.String)]
        [XmlElement(Order = 0)]
        [DataMember]
        public string SiglaProvincia
        {
            get { return this.siglaprovincia; }
            set { this.siglaprovincia = value; }
        }

        #endregion

        private string provincia = null;
        [DataField("PROVINCIA", Size = 20, Type = DbType.String)]
        [XmlElement(Order = 1)]
        [DataMember]
        public string PROVINCIA
        {
            get { return this.provincia; }
            set { this.provincia = value; }
        }
    }
}
