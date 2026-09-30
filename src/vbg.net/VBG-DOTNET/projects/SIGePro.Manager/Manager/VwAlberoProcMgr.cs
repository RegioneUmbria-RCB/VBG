using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcMgr.\n	/// </summary>
    public class VwAlberoProcMgr //: BaseManager
    {
        private readonly DataBase db;

        public VwAlberoProcMgr(DataBase dataBase) { this.db = dataBase; }

        public VwAlberoProc GetById(int? scId, String idComune)
        {
            VwAlberoProc retVal = new VwAlberoProc();
            retVal.ScId = scId;
            retVal.Idcomune = idComune;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }
    }
}
