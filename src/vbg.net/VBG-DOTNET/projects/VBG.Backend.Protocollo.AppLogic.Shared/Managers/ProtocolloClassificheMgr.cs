using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public partial class ProtocolloClassificheMgr : BaseProtocolloManager
    {
        public ProtocolloClassificheMgr(DataBase dataBase) : base(dataBase) { }

        public IEnumerable<ProtocolloClassifiche> GetBySoftwareCodiceComune(string idcomune, string software = "TT", string codiceComune = "")
        {
            var c = new ProtocolloClassifiche { Idcomune = idcomune };
            return this.GetByClassProtocollo<ProtocolloClassifiche>(c, software, codiceComune);
        }

        public ProtocolloClassifiche GetById(string idcomune, int id)
        {
            var c = new ProtocolloClassifiche { Idcomune = idcomune, Id = id };
            return (ProtocolloClassifiche)db.GetClass(c);
        }

        public ProtocolloClassifiche GetByCodice(string idcomune, string software, string codice)
        {
            var c = new ProtocolloClassifiche { Idcomune = idcomune, Software = software, Codice = codice };
            return (ProtocolloClassifiche)db.GetClass(c);
        }

        public List<ProtocolloClassifiche> GetList(ProtocolloClassifiche filtro)
        {
            return db.GetClassList(filtro).ToList<ProtocolloClassifiche>();
        }
    }
}
