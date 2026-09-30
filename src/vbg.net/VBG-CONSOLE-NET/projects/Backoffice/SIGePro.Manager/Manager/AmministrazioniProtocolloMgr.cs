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
    public partial class AmministrazioniProtocolloMgr : BaseProtocolloManager
    {
        public AmministrazioniProtocolloMgr(DataBase dataBase) : base(dataBase) { }

        public AmministrazioniProtocollo GetById(string idcomune, int codiceAmministrazione, string software = "TT", string codiceComune = "")
        {
            var c = new AmministrazioniProtocollo { Idcomune = idcomune, Codiceamministrazione = codiceAmministrazione };
            return this.GetByIdProtocollo<AmministrazioniProtocollo>(c, software, codiceComune);
        }

        public List<AmministrazioniProtocollo> GetList(AmministrazioniProtocollo filtro)
        {
            return db.GetClassList(filtro).ToList<AmministrazioniProtocollo>();
        }
    }
}


