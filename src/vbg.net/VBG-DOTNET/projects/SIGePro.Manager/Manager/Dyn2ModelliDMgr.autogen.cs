using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella DYN2_MODELLID per la classe Dyn2ModelliD il 05/08/2008 16.49.58
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
    public partial class Dyn2ModelliDMgr : BaseManager
    {
        public Dyn2ModelliDMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2ModelliD GetById(string idcomune, int id)
        {
            var c = new Dyn2ModelliD();


            c.Idcomune = idcomune;
            c.Id = id;

            return (Dyn2ModelliD)this.db.GetClass(c);
        }

        public List<Dyn2ModelliD> GetList(Dyn2ModelliD filtro)
        {
            return this.db.GetClassList(filtro).ToList<Dyn2ModelliD>();
        }

        public Dyn2ModelliD Insert(Dyn2ModelliD cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private Dyn2ModelliD ChildInsert(Dyn2ModelliD cls)
        {
            return cls;
        }

        private Dyn2ModelliD DataIntegrations(Dyn2ModelliD cls)
        {
            return cls;
        }


        public Dyn2ModelliD Update(Dyn2ModelliD cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }










    }
}


