using System;
using System.Data;

namespace Init.SIGePro.Manager.Authentication.Legacy
{
    public class SecurityListService
    {
        private readonly SigeproSecurityProxy _sigeproSecurityProxy;

        public SecurityListService(SigeproSecurityProxy sigeproSecurityProxy)
        {
            this._sigeproSecurityProxy = sigeproSecurityProxy;
        }

        [Obsolete]
        public DataSet GetSecurityList()
        {
            var ds = new DataSet();
            ds.Tables.Add("comunisecurity");
            ds.Tables[0].Columns.Add("cs_codiceistat", typeof(string));

            var secList = this._sigeproSecurityProxy.GetSecurityList();

            foreach (var it in secList)
            {
                if (it.attivo)
                {
                    var dr = ds.Tables[0].NewRow();
                    dr[0] = it.alias;

                    ds.Tables[0].Rows.Add(dr);
                }
            }

            return ds;
        }
    }
}
