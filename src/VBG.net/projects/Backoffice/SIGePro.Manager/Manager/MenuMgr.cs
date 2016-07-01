using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Authentication;

namespace Init.SIGePro.Manager.Manager
{
    public class MenuMgr : BaseManager
    {
        public MenuMgr( DataBase dataBase ) : base( dataBase ) {}

        /// <summary>
        /// Verifica che l'utente ed il software in questione siano abilitati 
        /// all'accesso della pagina specificata.
        /// </summary>
        /// <param name="pagina"></param>
        /// <param name="idUtente"></param>
        /// <param name="software"></param>
        /// <returns></returns>
        public bool SeAbilitato(string pagina, int? idUtente, string software, string idComune)
        {
            if (!idUtente.HasValue) return false;

            List<ClMenu> l = GetList(pagina, software);

            if (l.Count > 1) 
                throw new Exception("La ricerca della pagina nella tabella dei menù ha restituito più di un record");

            if (l.Count == 0) return true;

            PermessiMenuMgr perm = new PermessiMenuMgr(db);

            return perm.SeAbilitato(l[0].ID, idUtente, software, idComune);
        }

        private List<ClMenu> GetList(string pagina, string software)
        {
            ClMenu m = new ClMenu();
            m.SOFTWARE = software;
            m.OthersWhereClause.Add("UPPER(PAGINA) LIKE '%ASPXPAGE=" + pagina + "%'");

            List<ClMenu> retVal = db.GetClassList(m, null, false, false).ToList<ClMenu>();
            
            if(retVal.Count == 0)
            {
                m.SOFTWARE = "*";
                retVal = db.GetClassList(m, null, false, false).ToList<ClMenu>();
            }

            return retVal;
        }
    }
}
