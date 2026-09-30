using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella PERTINENZE_COEFFICIENTI per la classe PertinenzeCoefficienti il 11/11/2008 9.20.11
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
    public partial class PertinenzeCoefficientiMgr : BaseManager
    {
        public PertinenzeCoefficientiMgr(DataBase dataBase) : base(dataBase) { }

        public List<PertinenzeCoefficienti> GetList(PertinenzeCoefficienti filtro)
        {
            return this.db.GetClassList(filtro).ToList<PertinenzeCoefficienti>();
        }

        public PertinenzeCoefficienti Insert(PertinenzeCoefficienti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private PertinenzeCoefficienti ChildInsert(PertinenzeCoefficienti cls)
        {
            return cls;
        }

        private PertinenzeCoefficienti DataIntegrations(PertinenzeCoefficienti cls)
        {
            return cls;
        }


        public PertinenzeCoefficienti Update(PertinenzeCoefficienti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(PertinenzeCoefficienti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(PertinenzeCoefficienti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(PertinenzeCoefficienti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(PertinenzeCoefficienti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


