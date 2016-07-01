using System;
using System.Collections;
using System.Data;
using Init.SIGePro.Data;
using Init.Utils;
using PersonalLib2.Data;
using System.Collections.Generic;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{ 	///<summary>
    /// Descrizione di riepilogo per AlberoProcMgr.\n	/// </summary>
    public class VwAlberoProcMgr : BaseManager
    {

        public VwAlberoProcMgr(DataBase dataBase) : base(dataBase) { }

        public VwAlberoProc GetById(int? scId, String idComune)
        {
            VwAlberoProc retVal = new VwAlberoProc();
            retVal.ScId = scId;
            retVal.Idcomune = idComune;

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as VwAlberoProc;

            return null;
        }

        public VwAlberoProc GetByClass(VwAlberoProc p_class)
        {
            DataClassCollection mydc = db.GetClassList(p_class, true, false);
            if (mydc.Count != 0)
                return (db.GetClassList(p_class, true, false)[0]) as VwAlberoProc;

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

            return GetByClass(pClass);
        }

        public List<VwAlberoProc> GetList(VwAlberoProc p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<VwAlberoProc> GetList(VwAlberoProc p_class, VwAlberoProc p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<VwAlberoProc>();
        }

    }
}
