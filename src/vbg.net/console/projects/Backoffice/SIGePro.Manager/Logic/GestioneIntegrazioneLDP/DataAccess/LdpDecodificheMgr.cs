using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess
{
    public class LdpDecodificheMgr : BaseManager2
    {
        public LdpDecodificheMgr(DataBase db, string idComune)
            : base(db, idComune)
        {

        }

        internal LdpDecodifiche GetById(int id)
        {
            var sql = PreparaQueryParametrica("SELECT * FROM LDP_DECODIFICHE where idcomune = {0} and ID = {1}", "idComune", "id");

            return ExecuteInConnection(() =>
            {
                var cmdParams = new[] {
                    new CommandParameter("idComune", base.IdComune),
                    new CommandParameter("id", id)
                };

                using (var cmd = CreateCommand(sql, cmdParams))
                {
                    return Database.GetClass<LdpDecodifiche>(cmd);
                }
            });
        }
    }
}
