using Init.SIGePro.Manager.DTO;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Manager
{
    public class InpsInailMgr : BaseManager2
    {
        ILog _log = LogManager.GetLogger(typeof(InpsInailMgr));

        public InpsInailMgr(DataBase db, string idComune)
            :base(db, idComune)
        {
        }

        public IEnumerable<BaseDto<string,string>> GetElencoSediInps()
        {
            bool closeCnn = false;

            try
            {
                closeCnn = CheckConnectionState();

                var sql = "select CODICE, DESCRIZIONE from elencoinpsbase";

                using (var cmd = CreateCommand(sql))
                {
                    using(var dr = cmd.ExecuteReader())
                    {
                        var result = new List<BaseDto<string, string>>();

                        while(dr.Read())
                        {
                            result.Add(new BaseDto<string, string>(dr[0].ToString(), dr[1].ToString()));
                        }

                        return result;
                    }
                }
            }
            finally
            {
                CloseIfNeeded(closeCnn);
            }

        }

        public IEnumerable<BaseDto<string, string>> GetElencoSediInail()
        {
            bool closeCnn = false;

            try
            {
                closeCnn = CheckConnectionState();

                var sql = "select CODICE, DESCRIZIONE from elencoinailbase";

                using (var cmd = CreateCommand(sql))
                {
                    using (var dr = cmd.ExecuteReader())
                    {
                        var result = new List<BaseDto<string, string>>();

                        while (dr.Read())
                        {
                            result.Add(new BaseDto<string, string>(dr[0].ToString(), dr[1].ToString()));
                        }

                        return result;
                    }
                }
            }
            finally
            {
                CloseIfNeeded(closeCnn);
            }

        }
    }
}
