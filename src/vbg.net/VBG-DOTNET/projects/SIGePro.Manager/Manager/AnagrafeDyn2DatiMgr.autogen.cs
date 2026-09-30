using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ANAGRAFEDYN2DATI per la classe AnagrafeDyn2Dati il 25/11/2009 15.24.22
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
    public partial class AnagrafeDyn2DatiMgr : BaseManager
    {
        public AnagrafeDyn2DatiMgr(DataBase dataBase) : base(dataBase) { }

        public AnagrafeDyn2Dati GetById(string idcomune, int? codiceanagrafe, int? fk_d2c_id, int? indice, int indiceMolteplicita)
        {
            var c = new AnagrafeDyn2Dati();

            c.Idcomune = idcomune;
            c.Codiceanagrafe = codiceanagrafe;
            c.FkD2cId = fk_d2c_id;
            c.Indice = indice;
            c.IndiceMolteplicita = indiceMolteplicita;

            return (AnagrafeDyn2Dati)this.db.GetClass(c);
        }

        public List<AnagrafeDyn2Dati> GetList(AnagrafeDyn2Dati filtro)
        {
            return this.db.GetClassList(filtro).ToList<AnagrafeDyn2Dati>();
        }

        public AnagrafeDyn2Dati Insert(AnagrafeDyn2Dati cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AnagrafeDyn2Dati Update(AnagrafeDyn2Dati cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AnagrafeDyn2Dati cls)
        {
            this.db.Delete(cls);
        }
    }
}


