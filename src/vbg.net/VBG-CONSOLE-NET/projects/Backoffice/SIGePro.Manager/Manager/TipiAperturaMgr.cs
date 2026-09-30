using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipiAperturaMgr.\n	/// </summary>
    public class TipiAperturaMgr : BaseManager
    {

        public TipiAperturaMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipiApertura GetById(String pTA_ID, String pIDCOMUNE)
        {
            TipiApertura retVal = new TipiApertura();
            retVal.TA_ID = pTA_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipiApertura> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiApertura;

            return null;
        }

        #endregion
    }
}