using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CANONI_COEFFICIENTI per la classe CanoniCoefficienti il 11/11/2008 9.18.30
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
    public partial class CanoniCoefficientiMgr : BaseManager
    {
        public CanoniCoefficientiMgr(DataBase dataBase) : base(dataBase) { }

        public List<CanoniCoefficienti> GetList(CanoniCoefficienti filtro)
        {
            return this.db.GetClassList(filtro).ToList<CanoniCoefficienti>();
        }

        public CanoniCoefficienti Insert(CanoniCoefficienti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CanoniCoefficienti ChildInsert(CanoniCoefficienti cls)
        {
            return cls;
        }

        private CanoniCoefficienti DataIntegrations(CanoniCoefficienti cls)
        {
            return cls;
        }

        public CanoniCoefficienti Update(CanoniCoefficienti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CanoniCoefficienti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CanoniCoefficienti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CanoniCoefficienti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CanoniCoefficienti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


