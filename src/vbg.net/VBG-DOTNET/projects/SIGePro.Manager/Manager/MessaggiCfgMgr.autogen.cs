using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MESSAGGICFG per la classe MessaggiCfg il 23/11/2009 11.29.44
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
    public partial class MessaggiCfgMgr : BaseManager
    {
        public MessaggiCfgMgr(DataBase dataBase) : base(dataBase) { }

        public MessaggiCfg GetById(string idcomune, string software, string contesto, int id)
        {
            var c = new MessaggiCfg();

            c.Idcomune = idcomune;
            c.Software = software;
            c.Contesto = contesto;
            c.Id = id;

            return (MessaggiCfg)this.db.GetClass(c);
        }

        public List<MessaggiCfg> GetList(MessaggiCfg filtro)
        {
            return this.db.GetClassList(filtro).ToList<MessaggiCfg>();
        }

        public MessaggiCfg Insert(MessaggiCfg cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private MessaggiCfg ChildInsert(MessaggiCfg cls)
        {
            return cls;
        }

        private MessaggiCfg DataIntegrations(MessaggiCfg cls)
        {
            return cls;
        }


        public MessaggiCfg Update(MessaggiCfg cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(MessaggiCfg cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(MessaggiCfg cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(MessaggiCfg cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(MessaggiCfg cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


