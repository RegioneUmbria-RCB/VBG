using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public class SettoriMgr : BaseManager
    {
        public SettoriMgr(DataBase dataBase) : base(dataBase) { }

        public Settori GetById(String pCODICESETTORE, String pIDCOMUNE)
        {
            Settori retVal = new Settori();
            retVal.CODICESETTORE = pCODICESETTORE;
            retVal.IDCOMUNE = pIDCOMUNE;

            return this.db.GetClass(retVal);
        }

        public List<Settori> GetList(Settori p_class)
        {
            return this.db.GetClassList(p_class).ToList<Settori>();
        }

        public void Delete(Settori p_class)
        {
            this.db.Delete(p_class);
        }

        public Settori Insert(Settori p_class)
        {
            this.db.Insert(p_class);
            return p_class;
        }

        public Settori Update(Settori p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }
    }
}