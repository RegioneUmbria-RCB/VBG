using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_DOCUMENTICAT per la classe AlberoProcDocumentiCat il 06/11/2009 9.31.24
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
    public partial class AlberoProcDocumentiCatMgr : BaseManager
    {
        public AlberoProcDocumentiCatMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoProcDocumentiCat GetById(string idcomune, int? id)
        {
            var c = new AlberoProcDocumentiCat();


            c.Idcomune = idcomune;
            c.Id = id;

            return (AlberoProcDocumentiCat)this.db.GetClass(c);
        }

        public List<AlberoProcDocumentiCat> GetList(AlberoProcDocumentiCat filtro)
        {
            return this.db.GetClassList(filtro).ToList<AlberoProcDocumentiCat>();
        }

        public AlberoProcDocumentiCat Insert(AlberoProcDocumentiCat cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AlberoProcDocumentiCat Update(AlberoProcDocumentiCat cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AlberoProcDocumentiCat cls)
        {
            this.db.Delete(cls);
        }

        private void Validate(AlberoProcDocumentiCat cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


