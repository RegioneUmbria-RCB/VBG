

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_CONDIZIONI_ATTIVITA per la classe CCCondizioniAttivita il 27/06/2008 13.01.37
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
    public partial class CCCondizioniAttivitaMgr : BaseManager
    {
        public CCCondizioniAttivitaMgr(DataBase dataBase) : base(dataBase) { }

        public CCCondizioniAttivita GetById(string idcomune, int id)
        {
            var c = new CCCondizioniAttivita();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCCondizioniAttivita)this.db.GetClass(c);
        }

        public List<CCCondizioniAttivita> GetList(string idcomune, int id, string fk_at_codiceistat, string condizionewhere, string software)
        {
            var c = new CCCondizioniAttivita();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(fk_at_codiceistat)) c.FkAtCodiceistat = fk_at_codiceistat;
            if (!String.IsNullOrEmpty(condizionewhere)) c.Condizionewhere = condizionewhere;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<CCCondizioniAttivita>();
        }

        public List<CCCondizioniAttivita> GetList(CCCondizioniAttivita filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCCondizioniAttivita>();
        }

        public CCCondizioniAttivita Insert(CCCondizioniAttivita cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCCondizioniAttivita ChildInsert(CCCondizioniAttivita cls)
        {
            return cls;
        }

        private CCCondizioniAttivita DataIntegrations(CCCondizioniAttivita cls)
        {
            return cls;
        }


        public CCCondizioniAttivita Update(CCCondizioniAttivita cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCCondizioniAttivita cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCCondizioniAttivita cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(CCCondizioniAttivita cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


