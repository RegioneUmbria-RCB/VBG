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
    public partial class ProtocolloTipiDocMetadatiMgr : BaseManager
    {
        public ProtocolloTipiDocMetadatiMgr(DataBase dataBase) : base(dataBase) { }

        public ProtocolloTipiDocMetadati GetById(string idComune, int fktipodoc, string codiceMetadato)
        {
            var c = new ProtocolloTipiDocMetadati
            {
                IdComune = idComune,
                FkTipoDocumento = fktipodoc,
                CodiceMetadato = codiceMetadato
            };
            return (ProtocolloTipiDocMetadati)db.GetClass(c);
        }

        public IEnumerable<ProtocolloTipiDocMetadati> GetListFromTipoDoc(string idComune, int fkTipoDoc)
        {
            var c = new ProtocolloTipiDocMetadati
            {
                IdComune = idComune,
                FkTipoDocumento = fkTipoDoc,
            };
            return db.GetClassList(c).ToList<ProtocolloTipiDocMetadati>();
        }
    }
}


