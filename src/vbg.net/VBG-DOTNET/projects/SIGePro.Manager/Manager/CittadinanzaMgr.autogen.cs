using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CITTADINANZA per la classe Cittadinanza il 15/09/2010 12.14.36
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
    public partial class CittadinanzaMgr : BaseManager
    {
        public CittadinanzaMgr(DataBase dataBase) : base(dataBase) { }

        public Cittadinanza GetById(int? codice)
        {
            var c = new Cittadinanza();


            c.Codice = codice;

            return (Cittadinanza)this.db.GetClass(c);
        }

        public List<Cittadinanza> GetList(Cittadinanza filtro)
        {
            return this.db.GetClassList(filtro).ToList<Cittadinanza>();
        }

        public Cittadinanza Insert(Cittadinanza cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Cittadinanza ChildInsert(Cittadinanza cls)
        {
            return cls;
        }

        private Cittadinanza DataIntegrations(Cittadinanza cls)
        {
            return cls;
        }


        public Cittadinanza Update(Cittadinanza cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(Cittadinanza cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(Cittadinanza cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(Cittadinanza cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(Cittadinanza cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


