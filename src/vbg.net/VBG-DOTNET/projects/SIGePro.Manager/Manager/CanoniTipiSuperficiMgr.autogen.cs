using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    ///
    /// File generato automaticamente dalla tabella CANONI_TIPISUPERFICI per la classe CanoniTipiSuperfici il 11/11/2008 9.19.13
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
    public partial class CanoniTipiSuperficiMgr : BaseManager
    {
        public CanoniTipiSuperficiMgr(DataBase dataBase) : base(dataBase) { }

        public CanoniTipiSuperfici GetById(string idcomune, int id)
        {
            var c = new CanoniTipiSuperfici();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CanoniTipiSuperfici)this.db.GetClass(c);
        }

        public List<CanoniTipiSuperfici> GetList(CanoniTipiSuperfici filtro)
        {
            return this.db.GetClassList(filtro).ToList<CanoniTipiSuperfici>();
        }

        public CanoniTipiSuperfici Insert(CanoniTipiSuperfici cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CanoniTipiSuperfici ChildInsert(CanoniTipiSuperfici cls)
        {
            return cls;
        }

        public CanoniTipiSuperfici Update(CanoniTipiSuperfici cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CanoniTipiSuperfici cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CanoniTipiSuperfici cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

    }
}


