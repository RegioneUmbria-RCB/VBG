using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AmministrazioneResponsabileMgr.\n	/// </summary>
    public class AmministrazioniResponsabiliMgr : BaseManager
    {


        public AmministrazioniResponsabiliMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public AmministrazioniResponsabili GetById(string pIDCOMUNE, string pID)
        {
            AmministrazioniResponsabili retVal = new AmministrazioniResponsabili();
            retVal.IDCOMUNE = pIDCOMUNE;
            retVal.ID = pID;

            List<AmministrazioniResponsabili> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as AmministrazioniResponsabili;

            return null;
        }

        #endregion
    }
}