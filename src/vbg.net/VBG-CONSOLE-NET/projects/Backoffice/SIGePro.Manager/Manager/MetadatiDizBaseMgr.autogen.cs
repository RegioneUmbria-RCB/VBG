

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
    /// File generato automaticamente dalla tabella METADATI_DIZ_BASE per la classe MetadatiDizBase il 10/10/2014 14.56.35
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
    public partial class MetadatiDizBaseMgr : BaseManager
    {
        public MetadatiDizBaseMgr(DataBase dataBase) : base(dataBase) { }

        public MetadatiDizBase GetById(string id)
        {
            var c = new MetadatiDizBase { Id = id };
            return (MetadatiDizBase)db.GetClass(c);
        }

        public List<MetadatiDizBase> GetList(MetadatiDizBase filtro)
        {
            return db.GetClassList(filtro).ToList<MetadatiDizBase>();
        }
    }
}


