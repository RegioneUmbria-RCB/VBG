using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CANONI_CATEGORIE per la classe CanoniCategorie il 11/11/2008 9.18.14
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
    public partial class CanoniCategorieMgr : BaseManager
    {
        public CanoniCategorieMgr(DataBase dataBase) : base(dataBase) { }

        public CanoniCategorie GetById(string idcomune, int id)
        {
            var c = new CanoniCategorie();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CanoniCategorie)this.db.GetClass(c);
        }

        public List<CanoniCategorie> GetList(CanoniCategorie filtro)
        {
            return this.db.GetClassList(filtro).ToList<CanoniCategorie>();
        }

        public CanoniCategorie Insert(CanoniCategorie cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CanoniCategorie ChildInsert(CanoniCategorie cls)
        {
            return cls;
        }

        private CanoniCategorie DataIntegrations(CanoniCategorie cls)
        {
            return cls;
        }

        public CanoniCategorie Update(CanoniCategorie cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CanoniCategorie cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CanoniCategorie cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(CanoniCategorie cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


