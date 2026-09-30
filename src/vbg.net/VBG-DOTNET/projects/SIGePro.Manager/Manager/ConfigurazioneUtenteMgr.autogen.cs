using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CONFIGURAZIONEUTENTE per la classe ConfigurazioneUtente il 16/12/2008 10.40.00
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
    public partial class ConfigurazioneUtenteMgr : BaseManager
    {
        public ConfigurazioneUtenteMgr(DataBase dataBase) : base(dataBase) { }

        public ConfigurazioneUtente GetById(int codiceresponsabile, string nomeparametro, string idcomune)
        {
            var c = new ConfigurazioneUtente();


            c.Codiceresponsabile = codiceresponsabile;
            c.Nomeparametro = nomeparametro;
            c.Idcomune = idcomune;

            return (ConfigurazioneUtente)this.db.GetClass(c);
        }

        public List<ConfigurazioneUtente> GetList(ConfigurazioneUtente filtro)
        {
            return this.db.GetClassList(filtro).ToList<ConfigurazioneUtente>();
        }

        public ConfigurazioneUtente Insert(ConfigurazioneUtente cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private ConfigurazioneUtente ChildInsert(ConfigurazioneUtente cls)
        {
            return cls;
        }

        private ConfigurazioneUtente DataIntegrations(ConfigurazioneUtente cls)
        {
            return cls;
        }


        public ConfigurazioneUtente Update(ConfigurazioneUtente cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ConfigurazioneUtente cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(ConfigurazioneUtente cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(ConfigurazioneUtente cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ConfigurazioneUtente cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


