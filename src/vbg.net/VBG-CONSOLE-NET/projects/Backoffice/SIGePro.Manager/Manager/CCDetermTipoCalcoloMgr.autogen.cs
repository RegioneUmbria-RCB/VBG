

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
    /// File generato automaticamente dalla tabella CC_DETERMTIPOCALCOLO per la classe CCDetermTipoCalcolo il 27/06/2008 13.01.37
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
    public partial class CCDetermTipoCalcoloMgr : BaseManager
    {
        public CCDetermTipoCalcoloMgr(DataBase dataBase) : base(dataBase) { }

        public CCDetermTipoCalcolo GetById(string idcomune, int id)
        {
            CCDetermTipoCalcolo c = new CCDetermTipoCalcolo();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCDetermTipoCalcolo)db.GetClass(c);
        }

        public List<CCDetermTipoCalcolo> GetList(string idcomune, int id, string fk_occbti_id, string fk_occbde_id, string software, string fk_ccbtc_id)
        {
            CCDetermTipoCalcolo c = new CCDetermTipoCalcolo();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(fk_occbti_id)) c.FkOccbtiId = fk_occbti_id;
            if (!String.IsNullOrEmpty(fk_occbde_id)) c.FkOccbdeId = fk_occbde_id;
            if (!String.IsNullOrEmpty(software)) c.Software = software;
            if (!String.IsNullOrEmpty(fk_ccbtc_id)) c.FkCcbtcId = fk_ccbtc_id;


			return db.GetClassList(c).ToList < CCDetermTipoCalcolo>();
        }

        public List<CCDetermTipoCalcolo> GetList(CCDetermTipoCalcolo filtro)
        {
			return db.GetClassList(filtro).ToList < CCDetermTipoCalcolo>();
        }

        public CCDetermTipoCalcolo Insert(CCDetermTipoCalcolo cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CCDetermTipoCalcolo)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCDetermTipoCalcolo ChildInsert(CCDetermTipoCalcolo cls)
        {
            return cls;
        }

        private CCDetermTipoCalcolo DataIntegrations(CCDetermTipoCalcolo cls)
        {
            return cls;
        }


        public CCDetermTipoCalcolo Update(CCDetermTipoCalcolo cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CCDetermTipoCalcolo cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCDetermTipoCalcolo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCDetermTipoCalcolo cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


