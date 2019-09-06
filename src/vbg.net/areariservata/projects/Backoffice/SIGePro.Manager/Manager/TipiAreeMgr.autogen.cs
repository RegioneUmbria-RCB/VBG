

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
            TipiAree c = new TipiAree();


            c.Idcomune = idcomune;
            c.Codicetipoarea = codicetipoarea;

            return (TipiAree)db.GetClass(c);
        }

        public List<TipiAree> GetList(string idcomune, int codicetipoarea, string software, string tipoarea)
        {
            TipiAree c = new TipiAree();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Codicetipoarea = codicetipoarea;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            if (!String.IsNullOrEmpty(tipoarea)) c.Tipoarea = tipoarea;


			return db.GetClassList(c).ToList < TipiAree>();
        }

        public List<TipiAree> GetList(TipiAree filtro)
        {
			return db.GetClassList(filtro).ToList < TipiAree>();
        }

        public TipiAree Insert(TipiAree cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (TipiAree)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(TipiAree cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
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
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


