

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
            CCDettagliSuperficie c = new CCDettagliSuperficie();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCDettagliSuperficie)db.GetClass(c);
        }

        public List<CCDettagliSuperficie> GetList(string idcomune, int id, int fk_ccts_id, string descrizione, string note, string software)
        {
            CCDettagliSuperficie c = new CCDettagliSuperficie();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.FkCcTsId = fk_ccts_id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCDettagliSuperficie>();
        }

        public List<CCDettagliSuperficie> GetList(CCDettagliSuperficie filtro)
        {
			return db.GetClassList(filtro).ToList < CCDettagliSuperficie>();
        }

        public CCDettagliSuperficie Insert(CCDettagliSuperficie cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCDettagliSuperficie)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCDettagliSuperficie cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCDettagliSuperficie cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCDettagliSuperficie cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


