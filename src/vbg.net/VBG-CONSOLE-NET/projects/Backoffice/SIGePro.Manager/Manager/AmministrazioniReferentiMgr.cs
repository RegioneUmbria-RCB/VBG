using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per AmministrazioniReferentiMgr.
    /// </summary>
    public class AmministrazioniReferentiMgr : BaseManager
    {
        public AmministrazioniReferentiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public AmministrazioniReferenti GetById(string pIDCOMUNE, string pID)
        {
            AmministrazioniReferenti retVal = new AmministrazioniReferenti();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.ID = pID;

            List<AmministrazioniReferenti> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AmministrazioniReferenti;

            return null;
        }

        #endregion
    }
}
