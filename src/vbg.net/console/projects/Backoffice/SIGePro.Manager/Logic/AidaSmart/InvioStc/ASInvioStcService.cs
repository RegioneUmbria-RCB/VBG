using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart.InvioStc
{
    public class ASInvioStcService : BaseManager2
    {
        DataBase _dataBase;

        public ASInvioStcService(DataBase dataBase)
            :base(dataBase, String.Empty)
        {
            this._dataBase = dataBase;
        }

        public SDEProxyStcDto GetByAliasESoftware(string alias, string software)
        {
            return ExecuteInConnection(() => {
                var sql = PreparaQueryParametrica("select * from SDEPROXY_STC where alias={0} and software={1}", "alias", "software");

                using (var cmd = this._dataBase.CreateCommand(sql))
                {
                    CreateAndAddParameter(cmd, "alias", alias);
                    CreateAndAddParameter(cmd, "software", software);

                    using (var dr = cmd.ExecuteReader())
                    {
                        if (!dr.Read())
                        {
                            return null;
                        }

                        return new SDEProxyStcDto
                        {
                            UrlStc = dr.GetString("URL_STC"),
                            Username = dr.GetString("USERNAME"),
                            Password = dr.GetString("PASSWORD"),

                            SportelloMittente = new SDEProxyStcDto.RiferimentiSportello
                            {
                                IdNodo = dr.GetInt("IDNODO_MITT").Value,
                                IdEnte = dr.GetString("IDENTE_MITT"),
                                IdSportello = dr.GetString("IDSPORTELLO_MITT")
                            },

                            SportelloDestinatario = new SDEProxyStcDto.RiferimentiSportello
                            {
                                IdNodo = dr.GetInt("IDNODO_DEST").Value,
                                IdEnte = dr.GetString("IDENTE_DEST"),
                                IdSportello = dr.GetString("IDSPORTELLO_DEST")
                            }
                        };
                    }
                }
            });
        }
    }
}
