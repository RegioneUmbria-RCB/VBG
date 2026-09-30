using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("CATASTO")]
    [Serializable]
    [DataContract]
    public class Catasto : BaseDataClass
    {

        #region Key Fields

        private string codice = null;
        [KeyField("CODICE", Size = 1, Type = DbType.String)]
        [DataMember]
        public string CODICE
        {
            get { return this.codice; }
            set { this.codice = value; }
        }

        #endregion

        private string descrizione = null;
        [isRequired]
        [DataField("DESCRIZIONE", Size = 30, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        public string DESCRIZIONE
        {
            get { return this.descrizione; }
            set { this.descrizione = value; }
        }
    }
}