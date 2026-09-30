using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_TABELLA_CARATTERIST per la classe CCTabellaCaratterist il 27/06/2008 13.01.40
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
    public partial class CCTabellaCaratteristMgr : BaseManager
    {
        public CCTabellaCaratteristMgr(DataBase dataBase) : base(dataBase) { }

        public CCTabellaCaratterist GetById(string idcomune, int id)
        {
            var c = new CCTabellaCaratterist();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCTabellaCaratterist)this.db.GetClass(c);
        }

        //     public List<CCTabellaCaratterist> GetList(string idcomune, int id, string descrizione, decimal perc, string software)
        //     {
        //         CCTabellaCaratterist c = new CCTabellaCaratterist();
        //         if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //         c.Id = id;
        //         if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
        //         c.Perc = perc;
        //         if (!String.IsNullOrEmpty(software)) c.Software = software;


        //return db.GetClassList(c).ToList < CCTabellaCaratterist>();
        //     }

        public List<CCTabellaCaratterist> GetList(CCTabellaCaratterist filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCTabellaCaratterist>();
        }

        public CCTabellaCaratterist Insert(CCTabellaCaratterist cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCTabellaCaratterist ChildInsert(CCTabellaCaratterist cls)
        {
            return cls;
        }

        private CCTabellaCaratterist DataIntegrations(CCTabellaCaratterist cls)
        {
            return cls;
        }


        public CCTabellaCaratterist Update(CCTabellaCaratterist cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCTabellaCaratterist cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCTabellaCaratterist cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCTabellaCaratterist cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


