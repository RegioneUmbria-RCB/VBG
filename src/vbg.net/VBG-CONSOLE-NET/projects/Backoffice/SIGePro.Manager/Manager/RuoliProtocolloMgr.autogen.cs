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
    public partial class RuoliProtocolloMgr : BaseProtocolloManager
    {
        public RuoliProtocolloMgr(DataBase dataBase) : base(dataBase)
        {
                
        }

        public RuoliProtocollo GetById(string idcomune, int idRuolo, string software = "TT", string codiceComune = "")
        {
            var c = new RuoliProtocollo { IdComune = idcomune, IdRuolo = idRuolo };
            return this.GetByIdProtocollo<RuoliProtocollo>(c, software, codiceComune);
        }

        public List<RuoliProtocollo> GetList(RuoliProtocollo filtro)
        {
            return db.GetClassList(filtro).ToList<RuoliProtocollo>();
        }

    }
}
