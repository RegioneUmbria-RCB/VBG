using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
    public class CatastoMgr : BaseManager
    {

        public CatastoMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Catasto GetById(String pCODICE)
        {
            Catasto retVal = new Catasto();
            retVal.CODICE = pCODICE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public Catasto GetByClass(Catasto cls)
        {
            var mydc = this.db.GetClassList(cls, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        #endregion
    }
}

