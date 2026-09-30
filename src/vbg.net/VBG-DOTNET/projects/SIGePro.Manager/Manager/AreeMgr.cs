using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per AreeMgr.\n	/// </summary>
    public class AreeMgr : BaseManager
    {
        public AreeMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Aree GetById(String pCODICEAREA, String pIDCOMUNE)
        {
            Aree retVal = new Aree();
            retVal.CODICEAREA = pCODICEAREA;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }
        /*
        public Aree GetByClass(Aree pClass)
        {
            var mydc = this.db.GetClassList(pClass, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }
        */


        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<Aree> GetList(Aree p_class)
        {
            return this.db.GetClassList(p_class);
        }
        #endregion
    }
}
