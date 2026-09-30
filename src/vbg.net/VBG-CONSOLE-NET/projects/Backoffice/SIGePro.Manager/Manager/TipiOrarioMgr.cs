using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiOrarioMgr.\n	/// </summary>
    public class TipiOrarioMgr : BaseManager
    {

        public TipiOrarioMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipiOrario GetById(String pTO_ID, String pIDCOMUNE)
        {
            TipiOrario retVal = new TipiOrario();
            retVal.TO_ID = pTO_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiOrario> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiOrario;

            return null;
        }

        #endregion
    }
}