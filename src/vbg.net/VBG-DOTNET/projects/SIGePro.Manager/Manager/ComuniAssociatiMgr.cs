using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
    public class ComuniAssociatiMgr : BaseManager
    {

        public ComuniAssociatiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public ComuniAssociati GetById(String pCODICECOMUNE, String pIDCOMUNE)
        {
            ComuniAssociati retVal = new ComuniAssociati();
            retVal.CODICECOMUNE = pCODICECOMUNE;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<ComuniAssociati> GetList(ComuniAssociati p_class)
        {
            return this.db.GetClassList(p_class);
        }

        #endregion
    }
}