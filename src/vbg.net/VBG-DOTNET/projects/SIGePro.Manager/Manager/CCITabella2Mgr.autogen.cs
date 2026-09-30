using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ITABELLA2 per la classe CCITabella2 il 27/06/2008 13.01.39
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
    public partial class CCITabella2Mgr : BaseManager
    {
        public CCITabella2Mgr(DataBase dataBase) : base(dataBase) { }

        public CCITabella2 GetById(string idcomune, int id)
        {
            var c = new CCITabella2();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCITabella2)this.db.GetClass(c);
        }

        //public List<CCITabella2> GetList(string idcomune, int id, int codiceistanza, int fk_ccic_id, float superficie, int fk_ccds_id)
        //{
        //	CCITabella2 c = new CCITabella2();
        //	if(!String.IsNullOrEmpty(idcomune))c.Idcomune = idcomune;
        //	c.Id = id;
        //	c.Codiceistanza = codiceistanza;
        //	c.FkCcicId = fk_ccic_id;
        //	c.Superficie = superficie;
        //	c.FkCcdsId = fk_ccds_id;


        //	return db.GetClassList(c).ToList < CCITabella2>();
        //}

        public List<CCITabella2> GetList(CCITabella2 filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCITabella2>();
        }

        public CCITabella2 Insert(CCITabella2 cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCITabella2 ChildInsert(CCITabella2 cls)
        {
            return cls;
        }

        private CCITabella2 DataIntegrations(CCITabella2 cls)
        {
            return cls;
        }


        public CCITabella2 Update(CCITabella2 cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCITabella2 cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CCITabella2 cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CCITabella2 cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCITabella2 cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


