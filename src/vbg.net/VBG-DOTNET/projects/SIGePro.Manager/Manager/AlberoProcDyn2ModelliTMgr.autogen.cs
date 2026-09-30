using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT per la classe AlberoProcDyn2ModelliT il 05/08/2008 16.49.58
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
    public partial class AlberoProcDyn2ModelliTMgr : BaseManager
    {
        public AlberoProcDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public AlberoProcDyn2ModelliT GetById(string idcomune, int fk_sc_id, int fk_d2mt_id)
        {
            var c = new AlberoProcDyn2ModelliT();


            c.Idcomune = idcomune;
            c.FkScId = fk_sc_id;
            c.FkD2mtId = fk_d2mt_id;

            return (AlberoProcDyn2ModelliT)this.db.GetClass(c);
        }

        public List<AlberoProcDyn2ModelliT> GetList(AlberoProcDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<AlberoProcDyn2ModelliT>();
        }

        public AlberoProcDyn2ModelliT Insert(AlberoProcDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AlberoProcDyn2ModelliT Update(AlberoProcDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AlberoProcDyn2ModelliT cls)
        {
            this.db.Delete(cls);
        }

        private void Validate(AlberoProcDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


