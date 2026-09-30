

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_VALIDITACOEFFICIENTI per la classe OValiditaCoefficienti il 27/06/2008 13.01.37
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
    public partial class OValiditaCoefficientiMgr : BaseManager
    {
        public OValiditaCoefficientiMgr(DataBase dataBase) : base(dataBase) { }

        public OValiditaCoefficienti GetById(string idcomune, int id)
        {
            var c = new OValiditaCoefficienti();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OValiditaCoefficienti)this.db.GetClass(c);
        }

        public List<OValiditaCoefficienti> GetList(string idcomune, int id, string descrizione, DateTime datainiziovalidita, string software)
        {
            var c = new OValiditaCoefficienti();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.Datainiziovalidita = datainiziovalidita;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<OValiditaCoefficienti>();
        }

        public List<OValiditaCoefficienti> GetList(OValiditaCoefficienti filtro)
        {
            return this.db.GetClassList(filtro).ToList<OValiditaCoefficienti>();
        }

        public OValiditaCoefficienti Insert(OValiditaCoefficienti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OValiditaCoefficienti ChildInsert(OValiditaCoefficienti cls)
        {
            return cls;
        }

        private OValiditaCoefficienti DataIntegrations(OValiditaCoefficienti cls)
        {
            return cls;
        }


        public OValiditaCoefficienti Update(OValiditaCoefficienti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OValiditaCoefficienti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }


        private void EffettuaCancellazioneACascata(OValiditaCoefficienti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OValiditaCoefficienti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


