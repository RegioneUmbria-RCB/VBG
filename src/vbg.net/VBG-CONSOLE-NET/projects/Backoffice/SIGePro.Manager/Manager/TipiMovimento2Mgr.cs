using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiMovimento2Mgr.\n	/// </summary>
    public class TipiMovimento2Mgr : BaseManager
    {

        public TipiMovimento2Mgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipiMovimento2 GetById(String pCODICETIPO, String pIDCOMUNE)
        {
            TipiMovimento2 retVal = new TipiMovimento2();
            retVal.CODICETIPO = pCODICETIPO;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiMovimento2> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiMovimento2;

            return null;
        }

        #endregion
    }
}
