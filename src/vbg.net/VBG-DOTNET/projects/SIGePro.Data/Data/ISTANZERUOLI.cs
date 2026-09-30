using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZERUOLI")]
    [Serializable]
    [DataContract]
    public class IstanzeRuoli : BaseDataClass
    {
        #region Key Fields

        private string codiceistanza = null;
        [KeyField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        public string CODICEISTANZA
        {
            get { return this.codiceistanza; }
            set { this.codiceistanza = value; }
        }

        private string idruolo = null;
        [KeyField("IDRUOLO", Type = DbType.Decimal)]
        [DataMember]
        public string IDRUOLO
        {
            get { return this.idruolo; }
            set { this.idruolo = value; }
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

        #region foreign keys
        private Ruoli m_ruolo;
        [ForeignKey("IDCOMUNE, IDRUOLO", "IDCOMUNE, ID")]
        [DataMember]
        public Ruoli Ruolo
        {
            get { return this.m_ruolo; }
            set { this.m_ruolo = value; }
        }

        #endregion
    }
}