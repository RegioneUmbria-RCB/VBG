using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_CAUSALIRIDUZIONIR per la classe CcCausaliRiduzioniR il 06/03/2009 12.28.44
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
    public partial class CcCausaliRiduzioniRMgr : BaseManager
    {
        public CcCausaliRiduzioniRMgr(DataBase dataBase) : base(dataBase) { }

        public CcCausaliRiduzioniR GetById(string idcomune, int id)
        {
            var c = new CcCausaliRiduzioniR();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CcCausaliRiduzioniR)this.db.GetClass(c);
        }

        public List<CcCausaliRiduzioniR> GetList(CcCausaliRiduzioniR filtro)
        {
            return this.db.GetClassList(filtro).ToList<CcCausaliRiduzioniR>();
        }

        public CcCausaliRiduzioniR Insert(CcCausaliRiduzioniR cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }


        public CcCausaliRiduzioniR Update(CcCausaliRiduzioniR cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CcCausaliRiduzioniR cls)
        {
            this.VerificaRecordCollegati(cls);

            this.db.Delete(cls);
        }


        private void Validate(CcCausaliRiduzioniR cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


