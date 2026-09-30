

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
    /// File generato automaticamente dalla tabella CANONI_CONFIGAREE per la classe CanoniConfigAree il 17/12/2008 14.40.40
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
    public partial class CanoniConfigAreeMgr : BaseManager
    {
        public CanoniConfigAreeMgr(DataBase dataBase) : base(dataBase) { }

        public CanoniConfigAree GetById(string idcomune, int id)
        {
            CanoniConfigAree c = new CanoniConfigAree();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CanoniConfigAree)db.GetClass(c);
        }

        public List<CanoniConfigAree> GetList(CanoniConfigAree filtro)
        {
            return db.GetClassList(filtro).ToList<CanoniConfigAree>();
        }

        public CanoniConfigAree Insert(CanoniConfigAree cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CanoniConfigAree)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CanoniConfigAree ChildInsert(CanoniConfigAree cls)
        {
            return cls;
        }

        private CanoniConfigAree DataIntegrations(CanoniConfigAree cls)
        {
            return cls;
        }


        public CanoniConfigAree Update(CanoniConfigAree cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CanoniConfigAree cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(CanoniConfigAree cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CanoniConfigAree cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CanoniConfigAree cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


