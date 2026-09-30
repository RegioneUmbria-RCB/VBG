using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TitoliMgr.\n	/// </summary>
    public class ComuniSecurityMgr : BaseManager
    {

        public ComuniSecurityMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public ComuniSecurity GetById(string pCS_CODICEISTAT)
        {
            ComuniSecurity retVal = new ComuniSecurity();
            retVal.CS_CODICEISTAT = pCS_CODICEISTAT;

            List<ComuniSecurity> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as ComuniSecurity;

            return null;
        }

        public ComuniSecurity GetByClass(ComuniSecurity pClass)
        {
            List<ComuniSecurity> comunisecurity = this.db.GetClassList(pClass, true);
            if (comunisecurity.Count != 0)
                return (comunisecurity[0]) as ComuniSecurity;

            return null;
        }




        #endregion
    }
}

