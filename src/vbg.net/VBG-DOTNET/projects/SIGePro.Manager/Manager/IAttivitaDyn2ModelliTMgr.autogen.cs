using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella I_ATTIVITADYN2MODELLIT per la classe IAttivitaDyn2ModelliT il 05/08/2008 16.49.58
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
    public partial class IAttivitaDyn2ModelliTMgr : BaseManager
    {
        public IAttivitaDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public IAttivitaDyn2ModelliT GetById(string idcomune, int fk_d2mt_id, int fk_ia_id)
        {
            var c = new IAttivitaDyn2ModelliT();


            c.Idcomune = idcomune;
            c.FkD2mtId = fk_d2mt_id;
            c.FkIaId = fk_ia_id;

            return (IAttivitaDyn2ModelliT)this.db.GetClass(c);
        }

        public List<IAttivitaDyn2ModelliT> GetList(IAttivitaDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<IAttivitaDyn2ModelliT>();
        }

        public IAttivitaDyn2ModelliT Insert(IAttivitaDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private IAttivitaDyn2ModelliT ChildInsert(IAttivitaDyn2ModelliT cls)
        {
            return cls;
        }

        private IAttivitaDyn2ModelliT DataIntegrations(IAttivitaDyn2ModelliT cls)
        {
            return cls;
        }


        public IAttivitaDyn2ModelliT Update(IAttivitaDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }





        private void Validate(IAttivitaDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


