using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per DynTModelliMgr.\n	/// </summary>
    public class DynTModelliMgr : BaseManager
    {

        public DynTModelliMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public DynTModelli GetById(String pIDMODELLO, String pIDCOMUNE)
        {
            DynTModelli retVal = new DynTModelli();
            retVal.IDMODELLO = pIDMODELLO;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<DynTModelli> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as DynTModelli;

            return null;
        }


        #endregion
    }
}