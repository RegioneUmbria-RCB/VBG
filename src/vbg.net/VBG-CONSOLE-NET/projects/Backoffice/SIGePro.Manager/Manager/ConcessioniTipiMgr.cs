using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class ConcessioniTipiMgr : BaseManager
    {

        public ConcessioniTipiMgr(DataBase dataBase) : base(dataBase) { }

        public ConcessioniTipi GetById(String pTIPOCONCESSIONE)
        {
            ConcessioniTipi retVal = new ConcessioniTipi();
            retVal.TIPOCONCESSIONE = pTIPOCONCESSIONE;

            List<ConcessioniTipi> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as ConcessioniTipi;

            return null;
        }


    }
}