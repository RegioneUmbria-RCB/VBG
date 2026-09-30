

using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{
    public partial class AlberoProcRuoliMgr : BaseManager
    {
        public AlberoProcRuoliMgr(DataBase dataBase) : base(dataBase) { }

        public List<AlberoProcRuoli> GetList(AlberoProcRuoli filtro)
        {
            return db.GetClassList(filtro).ToList<AlberoProcRuoli>();
        }

    }
}


