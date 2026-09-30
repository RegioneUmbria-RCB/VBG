using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
    [DataTable("TIPICONTESTOESPORTAZIONE")]
    [Serializable]
    public class TIPICONTESTOESPORTAZIONE : BaseDataClass
    {
        #region Key Fields

        string codice = null;
        [KeyField("CODICE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string CODICE
        {
            get { return codice; }
            set { codice = value; }
        }

        #endregion

        string descrizione = null;
        [DataField("DESCRIZIONE", Size = 30, Type = DbType.String, Compare = "like", CaseSensitive = false)]
        public string DESCRIZIONE
        {
            get { return descrizione; }
            set { descrizione = value; }
        }

    }
}