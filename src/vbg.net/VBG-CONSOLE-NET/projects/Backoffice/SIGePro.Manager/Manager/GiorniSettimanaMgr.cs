using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per GiorniSettimanaMgr.
    /// </summary>
    public class GiorniSettimanaMgr : BaseManager
    {
        public GiorniSettimanaMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public GiorniSettimana GetById(String pGS_ID)
        {
            GiorniSettimana retVal = new GiorniSettimana();
            retVal.GS_ID = pGS_ID;

            List<GiorniSettimana> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as GiorniSettimana;

            return null;
        }

        #endregion
    }
}
