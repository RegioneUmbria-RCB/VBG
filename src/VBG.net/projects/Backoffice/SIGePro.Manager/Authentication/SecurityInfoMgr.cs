using System;
using System.Collections;
using Init.SIGePro.Utils;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using PersonalLib2.Data;

using System.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.WsSigeproSecurity;

namespace Init.SIGePro.Authentication
{
    /// <summary>
    /// Descrizione di riepilogo per SecurityInfoMgr.
    /// </summary>
    public static class SecurityInfoMgr
    {
        public static DataSet GetSecurityList()
        {
			var ds = new DataSet();
			ds.Tables.Add("comunisecurity");
			ds.Tables[0].Columns.Add("cs_codiceistat", typeof(string));

			var req = new GetSecurityListRequest
			{
			};

			var secList = SigeproSecurityProxy.GetSecurityList(req);

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
