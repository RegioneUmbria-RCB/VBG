

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_CONFIGURAZIONE_SETTORI per la classe CCConfigurazioneSettori il 27/06/2008 13.01.37
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
    public partial class CCConfigurazioneSettoriMgr : BaseManager
    {
        public CCConfigurazioneSettoriMgr(DataBase dataBase) : base(dataBase) { }

        public CCConfigurazioneSettori GetById(string idcomune, string software, string fk_se_codicesettore)
        {
            var c = new CCConfigurazioneSettori();


            c.Idcomune = idcomune;
            c.Software = software;
            c.FkSeCodicesettore = fk_se_codicesettore;

            return (CCConfigurazioneSettori)this.db.GetClass(c);
        }

        public List<CCConfigurazioneSettori> GetList(string idcomune, string software, string fk_se_codicesettore)
        {
            var c = new CCConfigurazioneSettori();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            if (!String.IsNullOrEmpty(fk_se_codicesettore)) c.FkSeCodicesettore = fk_se_codicesettore;


            return this.db.GetClassList(c).ToList<CCConfigurazioneSettori>();
        }

        public List<CCConfigurazioneSettori> GetList(CCConfigurazioneSettori filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCConfigurazioneSettori>();
        }

        public CCConfigurazioneSettori Insert(CCConfigurazioneSettori cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCConfigurazioneSettori ChildInsert(CCConfigurazioneSettori cls)
        {
            return cls;
        }

        private CCConfigurazioneSettori DataIntegrations(CCConfigurazioneSettori cls)
        {
            return cls;
        }


        public CCConfigurazioneSettori Update(CCConfigurazioneSettori cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCConfigurazioneSettori cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCConfigurazioneSettori cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCConfigurazioneSettori cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


