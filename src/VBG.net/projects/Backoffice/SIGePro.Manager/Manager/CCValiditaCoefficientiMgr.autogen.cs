

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
    /// File generato automaticamente dalla tabella CC_VALIDITACOEFFICIENTI per la classe CCValiditaCoefficienti il 02/07/2008 10.53.43
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
    public partial class CCValiditaCoefficientiMgr : BaseManager
    {
        public CCValiditaCoefficientiMgr(DataBase dataBase) : base(dataBase) { }

        public CCValiditaCoefficienti GetById(string idcomune, int id)
        {
            CCValiditaCoefficienti c = new CCValiditaCoefficienti();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCValiditaCoefficienti)db.GetClass(c);
        }

        public List<CCValiditaCoefficienti> GetList(string idcomune, int id, string descrizione, DateTime datainiziovalidita, string software, float costomq)
        {
            CCValiditaCoefficienti c = new CCValiditaCoefficienti();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.Datainiziovalidita = datainiziovalidita;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            c.Costomq = costomq;


			return db.GetClassList(c).ToList < CCValiditaCoefficienti>();
        }

        public List<CCValiditaCoefficienti> GetList(CCValiditaCoefficienti filtro)
        {
			return db.GetClassList(filtro).ToList < CCValiditaCoefficienti>();
        }

        public CCValiditaCoefficienti Insert(CCValiditaCoefficienti cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCValiditaCoefficienti)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCValiditaCoefficienti ChildInsert(CCValiditaCoefficienti cls)
        {
            return cls;
        }

        private CCValiditaCoefficienti DataIntegrations(CCValiditaCoefficienti cls)
        {
            return cls;
        }


        public CCValiditaCoefficienti Update(CCValiditaCoefficienti cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCValiditaCoefficienti cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCValiditaCoefficienti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCValiditaCoefficienti cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


