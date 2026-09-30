using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella DYN2_CAMPI_SCRIPT per la classe Dyn2CampiScript il 22/12/2008 12.25.48
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
    public partial class Dyn2CampiScriptMgr : BaseManager
    {
        public Dyn2CampiScriptMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2CampiScript GetById(string idcomune, int fk_d2c_id, string evento)
        {
            var c = new Dyn2CampiScript();


            c.Idcomune = idcomune;
            c.FkD2cId = fk_d2c_id;
            c.Evento = evento;

            return (Dyn2CampiScript)this.db.GetClass(c);
        }

        public List<Dyn2CampiScript> GetList(Dyn2CampiScript filtro)
        {
            return this.db.GetClassList(filtro).ToList<Dyn2CampiScript>();
        }

        public Dyn2CampiScript Insert(Dyn2CampiScript cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Dyn2CampiScript ChildInsert(Dyn2CampiScript cls)
        {
            return cls;
        }

        private Dyn2CampiScript DataIntegrations(Dyn2CampiScript cls)
        {
            return cls;
        }


        public Dyn2CampiScript Update(Dyn2CampiScript cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Dyn2CampiScript cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(Dyn2CampiScript cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Dyn2CampiScript cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Dyn2CampiScript cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


