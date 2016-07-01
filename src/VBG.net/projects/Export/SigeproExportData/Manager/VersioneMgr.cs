using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGeProExport.Data;

namespace Init.SIGeProExport.Manager
{
    public class VersioneMgr : BaseManager
    {
        public VersioneMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public string GetVersione()
        {
            var filtro = new VERSIONE();
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(filtro, true, false);

            if (mydc.Count != 0)
                return ((mydc[0]) as VERSIONE).Versione;

            return null;
        }

        #endregion
    }
}
