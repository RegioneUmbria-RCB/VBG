using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEREPLICATE")]
    public class IstanzeReplicate : BaseDataClass
    {
        #region Keyfield
        string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IdComune
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        int? codiceistanzapadre = null;
        [KeyField("CODICEISTANZAPADRE", Type = DbType.Decimal)]
        public int? CodiceIstanzaPadre
        {
            get { return codiceistanzapadre; }
            set { codiceistanzapadre = value; }
        }

        int? codiceistanzafiglia = null;
        [KeyField("CODICEISTANZAFIGLIA", Type = DbType.Decimal)]
        public int? CodiceIstanzaFiglia
        {
            get { return codiceistanzafiglia; }
            set { codiceistanzafiglia = value; }
        }
        #endregion
    }
}