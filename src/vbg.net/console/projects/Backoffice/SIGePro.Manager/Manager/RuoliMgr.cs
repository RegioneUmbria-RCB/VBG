

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
    /// File generato automaticamente dalla tabella RUOLI per la classe Ruoli il 07/10/2014 16.25.18
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
    public partial class RuoliMgr : BaseManager
    {
        public RuoliMgr(DataBase dataBase) : base(dataBase) { }

        public Ruoli GetById(string idcomune, int id)
        {
            var c = new Ruoli();

            c.IDCOMUNE = idcomune;
            c.ID = id.ToString();

            return (Ruoli)db.GetClass(c);
        }

        public IEnumerable<Ruoli> GetList(Ruoli filtro)
        {
            return db.GetClassList(filtro).ToList<Ruoli>();
        }
    }
}


