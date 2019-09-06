using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;
using System.Linq;

namespace Init.SIGePro.Manager
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


