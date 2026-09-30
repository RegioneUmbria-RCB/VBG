

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIAREE per la classe TipiAree il 27/06/2008 17.52.59
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
    public partial class TipiAreeMgr : BaseManager
    {
        public TipiAreeMgr(DataBase dataBase) : base(dataBase) { }

        public TipiAree GetById(string idcomune, int codicetipoarea)
        {
            var c = new TipiAree();


            c.Idcomune = idcomune;
            c.Codicetipoarea = codicetipoarea;

            return (TipiAree)this.db.GetClass(c);
        }

        public List<TipiAree> GetList(string idcomune, int codicetipoarea, string software, string tipoarea)
        {
            var c = new TipiAree();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Codicetipoarea = codicetipoarea;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            if (!String.IsNullOrEmpty(tipoarea)) c.Tipoarea = tipoarea;


            return this.db.GetClassList(c).ToList<TipiAree>();
        }

        public List<TipiAree> GetList(TipiAree filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiAree>();
        }

        public TipiAree Insert(TipiAree cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiAree ChildInsert(TipiAree cls)
        {
            return cls;
        }

        private TipiAree DataIntegrations(TipiAree cls)
        {
            return cls;
        }


        public TipiAree Update(TipiAree cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiAree cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiAree cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiAree cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiAree cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


