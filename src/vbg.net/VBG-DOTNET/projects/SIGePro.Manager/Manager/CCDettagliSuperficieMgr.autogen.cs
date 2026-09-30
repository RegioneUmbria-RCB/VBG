

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_DETTAGLISUPERFICIE per la classe CCDettagliSuperficie il 27/06/2008 13.01.37
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
    public partial class CCDettagliSuperficieMgr : BaseManager
    {
        public CCDettagliSuperficieMgr(DataBase dataBase) : base(dataBase) { }

        public CCDettagliSuperficie GetById(string idcomune, int id)
        {
            var c = new CCDettagliSuperficie();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCDettagliSuperficie)this.db.GetClass(c);
        }

        public List<CCDettagliSuperficie> GetList(string idcomune, int id, int fk_ccts_id, string descrizione, string note, string software)
        {
            var c = new CCDettagliSuperficie();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.FkCcTsId = fk_ccts_id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<CCDettagliSuperficie>();
        }

        public List<CCDettagliSuperficie> GetList(CCDettagliSuperficie filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCDettagliSuperficie>();
        }

        public CCDettagliSuperficie Insert(CCDettagliSuperficie cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCDettagliSuperficie ChildInsert(CCDettagliSuperficie cls)
        {
            return cls;
        }

        private CCDettagliSuperficie DataIntegrations(CCDettagliSuperficie cls)
        {
            return cls;
        }


        public CCDettagliSuperficie Update(CCDettagliSuperficie cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCDettagliSuperficie cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCDettagliSuperficie cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCDettagliSuperficie cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


