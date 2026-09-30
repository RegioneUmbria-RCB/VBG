using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

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
            var c = new CCClassiSuperfici();

            c.Idcomune = idcomune;
            c.Id = id;

            return (CCClassiSuperfici)this.db.GetClass(c);
        }

        public List<CCClassiSuperfici> GetList(CCClassiSuperfici filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCClassiSuperfici>();
        }

        public CCClassiSuperfici Insert(CCClassiSuperfici cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public CCClassiSuperfici Update(CCClassiSuperfici cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCClassiSuperfici cls)
        {
            this.VerificaRecordCollegati(cls);

            this.db.Delete(cls);
        }



        private void Validate(CCClassiSuperfici cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


