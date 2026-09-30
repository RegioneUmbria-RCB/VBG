using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TipologiaIstanzaMgr.\n	/// </summary>
    public class TipologiaIstanzaMgr : BaseManager
    {

        public TipologiaIstanzaMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TipologiaIstanza GetById(String pTI_ID, String pIDCOMUNE)
        {
            TipologiaIstanza retVal = new TipologiaIstanza();
            retVal.TI_ID = pTI_ID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<TipologiaIstanza> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipologiaIstanza;

            return null;
        }

        #endregion
    }
}
