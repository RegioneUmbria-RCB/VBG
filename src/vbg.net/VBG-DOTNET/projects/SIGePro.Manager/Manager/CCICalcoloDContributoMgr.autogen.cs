using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLO_DCONTRIBUTO per la classe CCICalcoloDContributo il 27/06/2008 13.01.38
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
    public partial class CCICalcoloDContributoMgr : BaseManager
    {
        public CCICalcoloDContributoMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoloDContributo? GetById(string idcomune, int id)
        {
            FormattableString sql = $"SELECT * FROM CC_ICALCOLO_DCONTRIBUTO WHERE IDCOMUNE = {idcomune} AND ID = {id}";

            return this.db.GetClassList<CCICalcoloDContributo>(sql).FirstOrDefault();
        }

        //public List<CCICalcoloDContributo> GetList(string idcomune, int id, int codiceistanza, int fk_ccictc_id, int fk_ccti_id, int fk_aree_codicearea, decimal coefficiente)
        //{
        //	CCICalcoloDContributo c = new CCICalcoloDContributo();
        //	if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
        //	c.Id = id;
        //	c.Codiceistanza = codiceistanza;
        //	c.FkCcictcId = fk_ccictc_id;
        //	c.FkCctiId = fk_ccti_id;
        //	c.FkAreeCodicearea = fk_aree_codicearea;
        //	c.Coefficiente = coefficiente;


        //	return db.GetClassList(c).ToList < CCICalcoloDContributo>();
        //}

        public List<CCICalcoloDContributo> GetList(CCICalcoloDContributo filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoloDContributo>();
        }

        public CCICalcoloDContributo Insert(CCICalcoloDContributo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCICalcoloDContributo ChildInsert(CCICalcoloDContributo cls)
        {
            return cls;
        }

        private CCICalcoloDContributo DataIntegrations(CCICalcoloDContributo cls)
        {
            return cls;
        }


        public CCICalcoloDContributo Update(CCICalcoloDContributo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCICalcoloDContributo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CCICalcoloDContributo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CCICalcoloDContributo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCICalcoloDContributo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }

        internal IEnumerable<CCICalcoloDContributo> GetListByIdTestataContributo(string idcomune, int idTestata)
        {
            FormattableString sql = $"SELECT * FROM CC_ICALCOLO_DCONTRIBUTO WHERE IDCOMUNE = {idcomune} AND FK_CCICTC_ID = {idTestata}";
            return this.db.GetClassList<CCICalcoloDContributo>(sql);
        }
    }
}


