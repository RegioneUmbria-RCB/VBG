using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGeProExport.Data
{
    [DataTable("VERSIONE")]
    [Serializable]
    public class VERSIONE : BaseDataClass
    {
        #region Key Fields
        string versione = null;
        [KeyField("VERSIONE", Size = 15, Type = DbType.String, CaseSensitive = false)]
        public string Versione
        {
            get { return versione; }
            set { versione = value; }
        }
        #endregion
    }

}
