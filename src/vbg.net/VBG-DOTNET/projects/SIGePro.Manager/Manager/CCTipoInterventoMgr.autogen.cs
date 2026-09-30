

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

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

        public CCTipoIntervento? GetById(string idcomune, int id)
        {
            FormattableString sql = $"SELECT * FROM CC_TIPOINTERVENTO WHERE IDCOMUNE = {idcomune} AND ID = {id}";

            return this.db.GetClassList<CCTipoIntervento>(sql).FirstOrDefault();
        }

        //public List<CCTipoIntervento> GetList(string idcomune, int id, string fk_occbti_id, string intervento, string software)
        //{
        //    var c = new CCTipoIntervento();
        //    if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //    c.Id = id;
        //    if (!String.IsNullOrEmpty(fk_occbti_id)) c.FkOccbtiId = fk_occbti_id;
        //    if (!String.IsNullOrEmpty(intervento)) c.Intervento = intervento;
        //    if (!String.IsNullOrEmpty(software)) c.Software = software;


        //    return this.db.GetClassList(c).ToList<CCTipoIntervento>();
        //}

        public List<CCTipoIntervento> GetList(CCTipoIntervento filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCTipoIntervento>();
        }

        public CCTipoIntervento Insert(CCTipoIntervento cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCTipoIntervento cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CCTipoIntervento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(CCTipoIntervento cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

        public IEnumerable<CCTipoIntervento> GetList(string idComune, string software)
        {
            FormattableString sql = $@"select * from CC_TIPOINTERVENTO where IDCOMUNE = {idComune} and SOFTWARE = {software} order by INTERVENTO asc";
            return this.db.GetClassList<CCTipoIntervento>(sql);
        }
    }
}


