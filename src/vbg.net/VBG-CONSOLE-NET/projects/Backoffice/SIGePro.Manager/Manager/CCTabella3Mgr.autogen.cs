

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
    /// File generato automaticamente dalla tabella CC_TABELLA3 per la classe CCTabella3 il 27/06/2008 13.01.40
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
    public partial class CCTabella3Mgr : BaseManager
    {
        public CCTabella3Mgr(DataBase dataBase) : base(dataBase) { }

        public CCTabella3 GetById(string idcomune, int id)
        {
            CCTabella3 c = new CCTabella3();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCTabella3)db.GetClass(c);
        }

        public List<CCTabella3> GetList(string idcomune, int id, string descrizione, int rapporto_su_snr_da, int rapporto_su_snr_a, float perc, string software)
        {
            CCTabella3 c = new CCTabella3();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.RapportoSuSnrDa = rapporto_su_snr_da;
            c.RapportoSuSnrA = rapporto_su_snr_a;
            c.Perc = perc;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCTabella3>();
        }

        public List<CCTabella3> GetList(CCTabella3 filtro)
        {
			return db.GetClassList(filtro).ToList < CCTabella3>();
        }

        public CCTabella3 Insert(CCTabella3 cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCTabella3)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCTabella3 ChildInsert(CCTabella3 cls)
        {
            return cls;
        }

        private CCTabella3 DataIntegrations(CCTabella3 cls)
        {
            return cls;
        }


        public CCTabella3 Update(CCTabella3 cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCTabella3 cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCTabella3 cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCTabella3 cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


