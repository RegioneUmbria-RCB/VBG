using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TESTIESTESI per la classe TestiEstesi il 30/11/2010 16.36.17
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
    public partial class TestiEstesiMgr : BaseManager
    {
        public TestiEstesiMgr(DataBase dataBase) : base(dataBase) { }

        public TestiEstesi GetById(string idcomune, int? id)
        {
            var c = new TestiEstesi();


            c.Idcomune = idcomune;
            c.Id = id;

            return (TestiEstesi)this.db.GetClass(c);
        }

        public List<TestiEstesi> GetList(TestiEstesi filtro)
        {
            return this.db.GetClassList(filtro).ToList<TestiEstesi>();
        }

        public TestiEstesi Insert(TestiEstesi cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TestiEstesi ChildInsert(TestiEstesi cls)
        {
            return cls;
        }

        private TestiEstesi DataIntegrations(TestiEstesi cls)
        {
            return cls;
        }


        public TestiEstesi Update(TestiEstesi cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TestiEstesi cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TestiEstesi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TestiEstesi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TestiEstesi cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


