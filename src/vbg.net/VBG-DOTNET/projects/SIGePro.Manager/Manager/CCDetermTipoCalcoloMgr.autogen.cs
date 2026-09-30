

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

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
            var c = new CCDetermTipoCalcolo();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCDetermTipoCalcolo)this.db.GetClass(c);
        }

        public List<CCDetermTipoCalcolo> GetList(string idcomune, string idTipoOntervento, string idDestinazioneBase, string software)
        {
            var c = new CCDetermTipoCalcolo();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            if (!String.IsNullOrEmpty(idTipoOntervento)) c.FkOccbtiId = idTipoOntervento;
            if (!String.IsNullOrEmpty(idDestinazioneBase)) c.FkOccbdeId = idDestinazioneBase;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c);
        }

        public List<CCDetermTipoCalcolo> GetList(CCDetermTipoCalcolo filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCDetermTipoCalcolo>();
        }

        public CCDetermTipoCalcolo Insert(CCDetermTipoCalcolo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCDetermTipoCalcolo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCDetermTipoCalcolo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCDetermTipoCalcolo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


