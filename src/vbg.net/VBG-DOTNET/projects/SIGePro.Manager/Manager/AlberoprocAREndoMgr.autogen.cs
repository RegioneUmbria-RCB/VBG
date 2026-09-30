using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_ARENDO per la classe AlberoprocAREndo il 29/08/2011 16.45.07
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
    public partial class AlberoprocAREndoMgr : BaseManager
    {
        public AlberoprocAREndoMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoprocAREndo GetById(string idcomune, int? id)
        {
            var c = new AlberoprocAREndo();


            c.Idcomune = idcomune;
            c.Id = id;

            return (AlberoprocAREndo)this.db.GetClass(c);
        }

        public List<AlberoprocAREndo> GetList(AlberoprocAREndo filtro)
        {
            return this.db.GetClassList(filtro).ToList<AlberoprocAREndo>();
        }

        public AlberoprocAREndo Insert(AlberoprocAREndo cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AlberoprocAREndo Update(AlberoprocAREndo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AlberoprocAREndo cls)
        {
            this.db.Delete(cls);
        }

        private void Validate(AlberoprocAREndo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


