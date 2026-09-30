using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_D per la classe IstanzeCalcoloCanoniD il 11/11/2008 9.19.52
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
    public partial class IstanzeCalcoloCanoniDMgr : BaseManager
    {
        public IstanzeCalcoloCanoniDMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeCalcoloCanoniD GetById(string idcomune, int id)
        {
            var c = new IstanzeCalcoloCanoniD();


            c.Idcomune = idcomune;
            c.Id = id;

            return (IstanzeCalcoloCanoniD)this.db.GetClass(c);
        }

        public List<IstanzeCalcoloCanoniD> GetList(IstanzeCalcoloCanoniD filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeCalcoloCanoniD>();
        }

        public IstanzeCalcoloCanoniD Insert(IstanzeCalcoloCanoniD cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IstanzeCalcoloCanoniD ChildInsert(IstanzeCalcoloCanoniD cls)
        {
            return cls;
        }

        private IstanzeCalcoloCanoniD DataIntegrations(IstanzeCalcoloCanoniD cls)
        {
            return cls;
        }


        public IstanzeCalcoloCanoniD Update(IstanzeCalcoloCanoniD cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeCalcoloCanoniD cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeCalcoloCanoniD cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(IstanzeCalcoloCanoniD cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(IstanzeCalcoloCanoniD cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


