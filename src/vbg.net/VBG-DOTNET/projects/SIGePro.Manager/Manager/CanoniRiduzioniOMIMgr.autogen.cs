using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CANONI_RIDUZIONIOMI per la classe CanoniRiduzioniOMI il 13/11/2008 14.38.32
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
    public partial class CanoniRiduzioniOMIMgr : BaseManager
    {
        public CanoniRiduzioniOMIMgr(DataBase dataBase) : base(dataBase) { }

        public CanoniRiduzioniOMI GetById(string idcomune, int id)
        {
            var c = new CanoniRiduzioniOMI();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CanoniRiduzioniOMI)this.db.GetClass(c);
        }

        public List<CanoniRiduzioniOMI> GetList(CanoniRiduzioniOMI filtro)
        {
            return this.db.GetClassList(filtro).ToList<CanoniRiduzioniOMI>();
        }

        public CanoniRiduzioniOMI Insert(CanoniRiduzioniOMI cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CanoniRiduzioniOMI ChildInsert(CanoniRiduzioniOMI cls)
        {
            return cls;
        }

        private CanoniRiduzioniOMI DataIntegrations(CanoniRiduzioniOMI cls)
        {
            return cls;
        }


        public CanoniRiduzioniOMI Update(CanoniRiduzioniOMI cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CanoniRiduzioniOMI cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CanoniRiduzioniOMI cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
    }
}


