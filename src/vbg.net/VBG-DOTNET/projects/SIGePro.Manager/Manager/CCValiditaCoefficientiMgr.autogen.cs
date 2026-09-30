

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{


    public partial class CCValiditaCoefficientiMgr
    {
        private readonly DataBase db;

        public CCValiditaCoefficientiMgr(DataBase dataBase)
        {
            this.db = dataBase;
        }

        public CCValiditaCoefficienti GetById(string idcomune, int id)
        {
            var c = new CCValiditaCoefficienti();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCValiditaCoefficienti)this.db.GetClass(c);
        }

        public List<CCValiditaCoefficienti> GetList(string idcomune, int id, string descrizione, DateTime datainiziovalidita, string software, float costomq)
        {
            var c = new CCValiditaCoefficienti();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.Datainiziovalidita = datainiziovalidita;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            c.Costomq = costomq;


            return this.db.GetClassList(c).ToList<CCValiditaCoefficienti>();
        }

        public List<CCValiditaCoefficienti> GetList(CCValiditaCoefficienti filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCValiditaCoefficienti>();
        }

        public CCValiditaCoefficienti Insert(CCValiditaCoefficienti cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public CCValiditaCoefficienti Update(CCValiditaCoefficienti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCValiditaCoefficienti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCValiditaCoefficienti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCValiditaCoefficienti cls, AmbitoValidazione ambitoValidazione)
        {
            ClassValidator.Validate(this.db, cls, ambitoValidazione);
        }
    }
}


