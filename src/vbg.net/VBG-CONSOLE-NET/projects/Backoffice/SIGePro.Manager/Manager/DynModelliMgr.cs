using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per DynModelliMgr.\n	/// </summary>
    public class DynModelliMgr : BaseManager
    {

        public DynModelliMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public DynModelli GetById(String pCODICE, String pIDCOMUNE)
        {
            DynModelli retVal = new DynModelli();
            retVal.CODICE = pCODICE;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<DynModelli> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as DynModelli;

            return null;
        }


        #endregion
    }
}