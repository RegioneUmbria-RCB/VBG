

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
    public partial class AlberoProcProtocolloMgr : BaseManager
    {
        public AlberoProcProtocolloMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoProcProtocollo GetById(string idcomune, int? id)
        {
            var c = new AlberoProcProtocollo { Idcomune = idcomune, Id = id };
            return (AlberoProcProtocollo)db.GetClass(c);
        }

        public AlberoProcProtocollo GetByCodiceComune(string idComune, int codiceAlberoProc, string codiceComune)
        {
            var c = new AlberoProcProtocollo { Idcomune = idComune, Fkscid = codiceAlberoProc };

            if (String.IsNullOrEmpty(codiceComune))
            {
                c.OthersWhereClause.Add("CODICECOMUNE IS NULL");
                return (AlberoProcProtocollo)db.GetClass(c);
            }
            else
            {
                c.CodiceComune = codiceComune;
                var res = (AlberoProcProtocollo)db.GetClass(c);

                if (res == null)
                {
                    c.CodiceComune = null;
                    c.OthersWhereClause.Add("CODICECOMUNE IS NULL");

                    return (AlberoProcProtocollo)db.GetClass(c);
                }
                else
                    return res;
            }
        }

        public List<AlberoProcProtocollo> GetList(AlberoProcProtocollo filtro)
        {
            return db.GetClassList(filtro).ToList<AlberoProcProtocollo>();
        }
    }
}


