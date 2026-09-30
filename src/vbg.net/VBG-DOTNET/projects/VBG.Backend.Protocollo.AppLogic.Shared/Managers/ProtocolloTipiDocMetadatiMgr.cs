using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
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
