

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

    ///
    /// File generato automaticamente dalla tabella PROT_TIPIDOCUMENTO_METADATI per la classe ProtTipiDocumentoMetadati il 10/10/2014 15.02.58
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    public partial class ProtTipiDocumentoMetadatiMgr : BaseManager
    {
        public ProtTipiDocumentoMetadatiMgr(DataBase dataBase) : base(dataBase) { }

        public ProtTipiDocumentoMetadati GetById(string idcomune, int? fkidprottpdoc, string fkidmetadatidizbase)
        {
            var c = new ProtTipiDocumentoMetadati
            {
                Idcomune = idcomune,
                Fkidprottpdoc = fkidprottpdoc,
                Fkidmetadatidizbase = fkidmetadatidizbase
            };
            return (ProtTipiDocumentoMetadati)db.GetClass(c);
        }

        public List<ProtTipiDocumentoMetadati> GetList(ProtTipiDocumentoMetadati filtro)
        {
            return db.GetClassList(filtro).ToList<ProtTipiDocumentoMetadati>();
        }
    }
}


