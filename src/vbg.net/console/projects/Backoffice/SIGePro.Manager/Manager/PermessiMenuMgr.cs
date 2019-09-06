using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Manager
{
    public class PermessiMenuMgr : BaseManager
    {
        public PermessiMenuMgr(DataBase dataBase) : base(dataBase) { }

        /// <summary>
        /// Verifica sulla tabella dei Permessi Menù che l'utente sia effettivamente abilitato
        /// alla gestione del menù passato nel primo parametro.
        /// </summary>
        /// <param name="idMenu"></param>
        /// <param name="idUtente"></param>
        /// <param name="software"></param>
        /// <returns></returns>
        internal bool SeAbilitato(string idMenu, int? idUtente, string software, string idComune)
        {
            if (!idUtente.HasValue) return false;
            ClPermMenu p = new ClPermMenu();

            p.IDCOMUNE = idComune;
            p.SOFTWARE = software;
            p.FKIDMENU = idMenu;
            p.CODICERESPONSABILE = idUtente.ToString();

            return GetList(p).Count > 0;
        }

        private List<ClPermMenu> GetList(ClPermMenu permMenu)
        {
            return db.GetClassList(permMenu, null, false, false).ToList<ClPermMenu>();
        }
    }
}
