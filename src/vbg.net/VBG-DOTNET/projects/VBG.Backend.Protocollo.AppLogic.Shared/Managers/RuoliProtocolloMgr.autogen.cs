using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
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
