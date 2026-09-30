using Init.SIGePro.Manager.Manager;
using PersonalLib2.Data;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess
{
    public class LdpDecodificheMgr : BaseManager2
    {
        public LdpDecodificheMgr(DataBase db, string idComune)
            : base(db, idComune)
        {

        }

        internal LdpDecodifiche? GetById(int id)
        {
            FormattableString sql = $"SELECT * FROM LDP_DECODIFICHE where idcomune = {base.IdComune} and ID = {id}";

            return this.Database.GetClassList<LdpDecodifiche>(sql).FirstOrDefault();
        }
    }
}
