

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_TIPISUPERFICIE per la classe CCTipiSuperficie il 27/06/2008 13.01.40
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
    public partial class CCTipiSuperficieMgr : BaseManager
    {
        public CCTipiSuperficieMgr(DataBase dataBase) : base(dataBase) { }

        public CCTipiSuperficie GetById(string idcomune, int id)
        {
            var c = new CCTipiSuperficie();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCTipiSuperficie)this.db.GetClass(c);
        }

        public List<CCTipiSuperficie> GetList(string idcomune, int id, string descrizione, string note, string software)
        {
            var c = new CCTipiSuperficie();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<CCTipiSuperficie>();
        }

        public List<CCTipiSuperficie> GetList(CCTipiSuperficie filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCTipiSuperficie>();
        }

        public CCTipiSuperficie Insert(CCTipiSuperficie cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCTipiSuperficie ChildInsert(CCTipiSuperficie cls)
        {
            return cls;
        }

        private CCTipiSuperficie DataIntegrations(CCTipiSuperficie cls)
        {
            return cls;
        }


        public CCTipiSuperficie Update(CCTipiSuperficie cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCTipiSuperficie cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void Validate(CCTipiSuperficie cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


