using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TitoliMgr.\n	/// </summary>
    public class ComuniSecurity_ConnectionMgr : BaseManager
    {

        public ComuniSecurity_ConnectionMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public ComuniSecurity_Connection GetById(string pCS_CODICEISTAT, string pAMBIENTE)
        {
            ComuniSecurity_Connection retVal = new ComuniSecurity_Connection();
            retVal.CS_CONNECTION_CODICEISTAT = pCS_CODICEISTAT;
            retVal.CS_CONNECTION_AMBIENTE = pAMBIENTE;

            List<ComuniSecurity_Connection> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as ComuniSecurity_Connection;

            return null;
        }


        #endregion
    }
}

