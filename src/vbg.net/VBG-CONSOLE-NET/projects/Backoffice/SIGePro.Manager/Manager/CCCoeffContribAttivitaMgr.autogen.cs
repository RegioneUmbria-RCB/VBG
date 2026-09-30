

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
    /// File generato automaticamente dalla tabella CC_COEFFCONTRIB_ATTIVITA per la classe CCCoeffContribAttivita il 01/07/2008 16.07.07
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
    public partial class CCCoeffContribAttivitaMgr : BaseManager
    {
        public CCCoeffContribAttivitaMgr(DataBase dataBase) : base(dataBase) { }

        public CCCoeffContribAttivita GetById(string idcomune, int id)
        {
            CCCoeffContribAttivita c = new CCCoeffContribAttivita();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCCoeffContribAttivita)db.GetClass(c);
        }

        public List<CCCoeffContribAttivita> GetList(string idcomune, int id, int fk_ccvc_id, int fk_ccde_id, int fk_ccca_id, float coefficiente, string software)
        {
            CCCoeffContribAttivita c = new CCCoeffContribAttivita();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.FkCcvcId = fk_ccvc_id;
            c.FkCcdeId = fk_ccde_id;
            c.FkCccaId = fk_ccca_id;
            c.Coefficiente = coefficiente;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCCoeffContribAttivita>();
        }

        public List<CCCoeffContribAttivita> GetList(CCCoeffContribAttivita filtro)
        {
			return db.GetClassList(filtro).ToList < CCCoeffContribAttivita>();
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCCoeffContribAttivita ChildInsert(CCCoeffContribAttivita cls)
        {
            return cls;
        }

        private void EffettuaCancellazioneACascata(CCCoeffContribAttivita cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCCoeffContribAttivita cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


