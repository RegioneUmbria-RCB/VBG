using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("RUOLI")]
    [Serializable]
    [DataContract]
    public class Ruoli : BaseDataClass
    {

        #region Key Fields

        private string id = null;
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        [DataMember]
        public string ID
        {
            get { return this.id; }
            set { this.id = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        #region Data Fields
        private string ruolo = null;
        [isRequired]
        [DataField("RUOLO", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        public string RUOLO
        {
            get { return this.ruolo; }
            set { this.ruolo = value; }
        }

        private string p_readonly = null;
        [isRequired]
        [DataField("READONLY", Type = DbType.Decimal)]
        [DataMember]
        public string READONLY
        {
            get { return this.p_readonly; }
            set { this.p_readonly = value; }
        }

        [DataField("COD_DOCER", Type = DbType.Decimal)]
        [DataMember]
        public string COD_DOCER { get; set; }

        #endregion
    }
}