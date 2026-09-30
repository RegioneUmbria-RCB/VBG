using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_BASETIPOCALCOLO per la classe CCBaseTipoCalcolo il 27/06/2008 16.48.53
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
    public partial class CCBaseTipoCalcoloMgr : BaseManager
    {
        public CCBaseTipoCalcoloMgr(DataBase dataBase) : base(dataBase) { }

        public CCBaseTipoCalcolo GetById(string id)
        {
            var c = new CCBaseTipoCalcolo();


            c.Id = id;

            return (CCBaseTipoCalcolo)this.db.GetClass(c);
        }

        public List<CCBaseTipoCalcolo> GetList(CCBaseTipoCalcolo filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCBaseTipoCalcolo>();
        }




    }
}


