using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per DynCampiMgr.\n	/// </summary>
    public class DynCampiMgr : BaseManager
    {

        public DynCampiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public DynCampi GetById(String pCODICE, String pIDCOMUNE)
        {
            DynCampi retVal = new DynCampi();
            retVal.CODICE = pCODICE;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<DynCampi> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as DynCampi;

            return null;
        }


        #endregion
    }
}