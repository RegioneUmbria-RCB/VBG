

using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

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
            CCCondizioniAttivita c = new CCCondizioniAttivita();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCCondizioniAttivita)db.GetClass(c);
        }

        public List<CCCondizioniAttivita> GetList(string idcomune, int id, string fk_at_codiceistat, string condizionewhere, string software)
        {
            CCCondizioniAttivita c = new CCCondizioniAttivita();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(fk_at_codiceistat)) c.FkAtCodiceistat = fk_at_codiceistat;
            if (!String.IsNullOrEmpty(condizionewhere)) c.Condizionewhere = condizionewhere;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCCondizioniAttivita>();
        }

        public List<CCCondizioniAttivita> GetList(CCCondizioniAttivita filtro)
        {
			return db.GetClassList(filtro).ToList < CCCondizioniAttivita>();
        }

        public CCCondizioniAttivita Insert(CCCondizioniAttivita cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCCondizioniAttivita)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCCondizioniAttivita cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCCondizioniAttivita cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
        
        private void Validate(CCCondizioniAttivita cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


