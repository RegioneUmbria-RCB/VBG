using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per TipiOrarioDettaglioMgr.
    /// </summary>
    public class TipiOrarioDettaglioMgr : BaseManager
    {
        public TipiOrarioDettaglioMgr(DataBase dataBase) : base(dataBase) { }


        #region Metodi per l'accesso di base al DB
        public TipiOrarioDettaglio GetById(String pOA_ID, String pIDCOMUNE)
        {
            TipiOrarioDettaglio retVal = new TipiOrarioDettaglio();
            retVal.OA_ID = pOA_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiOrarioDettaglio> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiOrarioDettaglio;

            return null;
        }

        #endregion
    }
}
