using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcOneriMgr.\n	/// </summary>
    public class AlberoProcOneriMgr : BaseManager
    {


        public AlberoProcOneriMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public AlberoProcOneri GetById(String pIDCOMUNE, String pAO_ID)
        {
            AlberoProcOneri retVal = new AlberoProcOneri();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.AO_ID = pAO_ID;

            List<AlberoProcOneri> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AlberoProcOneri;

            return null;



        }


        #endregion
    }
}