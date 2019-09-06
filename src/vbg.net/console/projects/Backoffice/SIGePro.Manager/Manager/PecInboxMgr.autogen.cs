

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
    /// File generato automaticamente dalla tabella PROTOCOLLO_PECINBOX per la classe PecInbox il 12/08/2015 11.14.41
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
    public partial class PecInboxMgr : BaseProtocolloManager
    {
        public PecInboxMgr(DataBase dataBase) : base(dataBase) { }

        public PecInbox GetById(string codicePec, string idcomune)
        {
            var c = new PecInbox { Idcomune = idcomune, Id = codicePec};
            return (PecInbox)db.GetClass(c);
        }

        public List<PecInbox> GetList(PecInbox filtro)
        {
            return db.GetClassList(filtro).ToList<PecInbox>();
        }

    }
}


