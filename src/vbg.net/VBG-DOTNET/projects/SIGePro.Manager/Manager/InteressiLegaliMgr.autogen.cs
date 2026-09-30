using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella INTERESSI_LEGALI per la classe InteressiLegali il 20/07/2009 14.56.51
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
    public partial class InteressiLegaliMgr : BaseManager
    {
        public InteressiLegaliMgr(DataBase dataBase) : base(dataBase) { }

        public InteressiLegali GetById(int id)
        {
            var c = new InteressiLegali();


            c.Id = id;

            return (InteressiLegali)this.db.GetClass(c);
        }

        public List<InteressiLegali> GetList(InteressiLegali filtro)
        {
            return this.db.GetClassList(filtro).ToList<InteressiLegali>();
        }

        public InteressiLegali Insert(InteressiLegali cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private InteressiLegali ChildInsert(InteressiLegali cls)
        {
            return cls;
        }

        private InteressiLegali DataIntegrations(InteressiLegali cls)
        {
            return cls;
        }


        public InteressiLegali Update(InteressiLegali cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(InteressiLegali cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(InteressiLegali cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(InteressiLegali cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(InteressiLegali cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


