using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class Dyn2ModelliTMgr : BaseManager
    {
        public Dyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2ModelliT GetById(string idcomune, int id)
        {
            var c = new Dyn2ModelliT
            {
                Idcomune = idcomune,
                Id = id
            };

            return this.db.GetClass(c);
        }

        public List<Dyn2ModelliT> GetList(Dyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<Dyn2ModelliT>();
        }

        public Dyn2ModelliT Insert(Dyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);


            cls = this.ChildInsert(cls);

            return cls;
        }

        private Dyn2ModelliT ChildInsert(Dyn2ModelliT cls)
        {
            return cls;
        }

        private Dyn2ModelliT DataIntegrations(Dyn2ModelliT cls)
        {
            return cls;
        }

        public Dyn2ModelliT Update(Dyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }
    }
}