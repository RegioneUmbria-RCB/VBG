using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcLimitiMgr.\n	/// </summary>
    public class AlberoProcLimitiMgr : BaseManager
    {
        public AlberoProcLimitiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public AlberoProcLimiti GetById(String pFKIDALBERO, String pFKIDZONA, String pIDCOMUNE)
        {
            AlberoProcLimiti retVal = new AlberoProcLimiti();

            retVal.FKIDALBERO = pFKIDALBERO;
            retVal.FKIDZONA = pFKIDZONA;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<AlberoProcLimiti> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AlberoProcLimiti;

            return null;
        }




        #endregion
    }
}