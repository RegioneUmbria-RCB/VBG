

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
    /// File generato automaticamente dalla tabella CC_TIPOINTERVENTO per la classe CCTipoIntervento il 27/06/2008 13.01.40
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
    public partial class CCTipoInterventoMgr : BaseManager
    {
        public CCTipoInterventoMgr(DataBase dataBase) : base(dataBase) { }

        public CCTipoIntervento GetById(string idcomune, int id)
        {
            CCTipoIntervento c = new CCTipoIntervento();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCTipoIntervento)db.GetClass(c);
        }

        public List<CCTipoIntervento> GetList(string idcomune, int id, string fk_occbti_id, string intervento, string software)
        {
            CCTipoIntervento c = new CCTipoIntervento();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(fk_occbti_id)) c.FkOccbtiId = fk_occbti_id;
            if (!String.IsNullOrEmpty(intervento)) c.Intervento = intervento;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCTipoIntervento>();
        }

        public List<CCTipoIntervento> GetList(CCTipoIntervento filtro)
        {
			return db.GetClassList(filtro).ToList < CCTipoIntervento>();
        }

        public CCTipoIntervento Insert(CCTipoIntervento cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCTipoIntervento)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCTipoIntervento ChildInsert(CCTipoIntervento cls)
        {
            return cls;
        }

        private CCTipoIntervento DataIntegrations(CCTipoIntervento cls)
        {
            return cls;
        }

        public CCTipoIntervento Update(CCTipoIntervento cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCTipoIntervento cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCTipoIntervento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(CCTipoIntervento cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


