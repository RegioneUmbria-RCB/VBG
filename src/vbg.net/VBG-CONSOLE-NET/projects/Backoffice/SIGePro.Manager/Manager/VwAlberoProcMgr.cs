using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AlberoProcMgr.\n	/// </summary>
    public class VwAlberoProcMgr : BaseManager
    {

        public VwAlberoProcMgr(DataBase dataBase) : base(dataBase) { }

        public VwAlberoProc GetById(int? scId, String idComune)
        {
            VwAlberoProc retVal = new VwAlberoProc();
            retVal.ScId = scId;
            retVal.Idcomune = idComune;

            List<VwAlberoProc> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as VwAlberoProc;

            return null;
        }

        public VwAlberoProc GetByClass(VwAlberoProc p_class)
        {
            List<VwAlberoProc> mydc = this.db.GetClassList(p_class, true);
            if (mydc.Count != 0)
                return (this.db.GetClassList(p_class, true)[0]) as VwAlberoProc;

            return null;
        }

        public VwAlberoProc GetByScCodice(String idComune, String software, String scCodice)
        {
            if (string.IsNullOrEmpty(idComune))
                throw new RequiredFieldException("Impossibile utilizzare VwAlberoProcMgr.GetByScCodice senza impostare il parametro idComune");

            if (string.IsNullOrEmpty(software))
                throw new RequiredFieldException("Impossibile utilizzare VwAlberoProcMgr.GetByScCodice senza impostare il parametro software");

            if (string.IsNullOrEmpty(scCodice))
                throw new RequiredFieldException("Impossibile utilizzare VwAlberoProcMgr.GetByScCodice senza impostare il parametro scCodice");

            VwAlberoProc pClass = new VwAlberoProc();
            pClass.Idcomune = idComune;
            pClass.Software = software;
            pClass.ScCodice = scCodice;

            return this.GetByClass(pClass);
        }

        public List<VwAlberoProc> GetList(VwAlberoProc p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<VwAlberoProc> GetList(VwAlberoProc p_class, VwAlberoProc p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false).ToList<VwAlberoProc>();
        }

    }
}
