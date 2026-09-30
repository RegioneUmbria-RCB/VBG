using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AzioniMgr.\n	/// </summary>
    public class AzioniMgr : BaseManager
    {

        public AzioniMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public Azioni GetById(String pAZ_ID)
        {
            Azioni retVal = new Azioni();
            retVal.AZ_ID = pAZ_ID;

            List<Azioni> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Azioni;

            return null;
        }

        #endregion
    }
}