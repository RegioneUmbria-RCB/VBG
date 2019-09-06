

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
    /// File generato automaticamente dalla tabella RI_CARICHE per la classe RiCariche il 25/08/2014 12.34.32
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
    public partial class RiCaricheMgr : BaseManager
    {
        public RiCaricheMgr(DataBase dataBase) : base(dataBase) { }

        public RiCariche GetById(string codice)
        {
            RiCariche c = new RiCariche();


            c.Codice = codice;

            return (RiCariche)db.GetClass(c);
        }

        public List<RiCariche> GetList(RiCariche filtro)
        {
            return db.GetClassList(filtro).ToList<RiCariche>();
        }
    }
}


