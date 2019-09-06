

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
    /// File generato automaticamente dalla tabella CC_CLASSISUPERFICI per la classe CCClassiSuperfici il 28/06/2008 11.33.08
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
    public partial class CCClassiSuperficiMgr : BaseManager
    {
        public CCClassiSuperficiMgr(DataBase dataBase) : base(dataBase) { }

        public CCClassiSuperfici GetById(string idcomune, int id)
        {
            CCClassiSuperfici c = new CCClassiSuperfici();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCClassiSuperfici)db.GetClass(c);
        }

        public List<CCClassiSuperfici> GetList(string idcomune, int id, int da, int a, string classe, int incremento, string software)
        {
            CCClassiSuperfici c = new CCClassiSuperfici();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.Da = da;
            c.A = a;
            if (!String.IsNullOrEmpty(classe)) c.Classe = classe;
            c.Incremento = incremento;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCClassiSuperfici>();
        }

        public List<CCClassiSuperfici> GetList(CCClassiSuperfici filtro)
        {
			return db.GetClassList(filtro).ToList < CCClassiSuperfici>();
        }

        public CCClassiSuperfici Insert(CCClassiSuperfici cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCClassiSuperfici)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCClassiSuperfici ChildInsert(CCClassiSuperfici cls)
        {
            return cls;
        }

        private CCClassiSuperfici DataIntegrations(CCClassiSuperfici cls)
        {
            return cls;
        }


        public CCClassiSuperfici Update(CCClassiSuperfici cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCClassiSuperfici cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCClassiSuperfici cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCClassiSuperfici cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


