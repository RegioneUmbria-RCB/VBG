using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLO_DCONTRIBATTIV per la classe CCICalcoloDContribAttiv il 27/06/2008 13.01.38
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
    public partial class CCICalcoloDContribAttivMgr : BaseManager
    {
        public CCICalcoloDContribAttivMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoloDContribAttiv GetById(string idcomune, int id)
        {
            var c = new CCICalcoloDContribAttiv();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCICalcoloDContribAttiv)this.db.GetClass(c);
        }

        //public List<CCICalcoloDContribAttiv> GetList(string idcomune, int id, int codiceistanza, int fk_ccictc_id, int fk_cccca_id, float coefficiente)
        //{
        //	CCICalcoloDContribAttiv c = new CCICalcoloDContribAttiv();
        //	if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
        //	c.Id = id;
        //	c.Codiceistanza = codiceistanza;
        //	c.FkCcictcId = fk_ccictc_id;
        //	c.FkCcccaId = fk_cccca_id;
        //	c.Coefficiente = coefficiente;


        //	return db.GetClassList(c).ToList < CCICalcoloDContribAttiv>();
        //}

        public List<CCICalcoloDContribAttiv> GetList(CCICalcoloDContribAttiv filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoloDContribAttiv>();
        }

        public CCICalcoloDContribAttiv Insert(CCICalcoloDContribAttiv cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCICalcoloDContribAttiv ChildInsert(CCICalcoloDContribAttiv cls)
        {
            return cls;
        }

        private CCICalcoloDContribAttiv DataIntegrations(CCICalcoloDContribAttiv cls)
        {
            return cls;
        }


        public CCICalcoloDContribAttiv Update(CCICalcoloDContribAttiv cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCICalcoloDContribAttiv cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CCICalcoloDContribAttiv cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CCICalcoloDContribAttiv cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCICalcoloDContribAttiv cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


