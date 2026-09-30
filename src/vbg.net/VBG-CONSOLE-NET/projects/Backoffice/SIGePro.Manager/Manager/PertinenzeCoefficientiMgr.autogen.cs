


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
            return db.GetClassList(filtro).ToList<PertinenzeCoefficienti>();
        }

        public PertinenzeCoefficienti Insert(PertinenzeCoefficienti cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (PertinenzeCoefficienti)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(PertinenzeCoefficienti cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
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
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


