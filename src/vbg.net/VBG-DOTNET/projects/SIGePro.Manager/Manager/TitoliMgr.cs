using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per TitoliMgr.\n	/// </summary>
    public class TitoliMgr : BaseManager
    {

        public TitoliMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB


        public Titoli GetById(String pCODICETITOLO, String pIDCOMUNE)
        {
            Titoli retVal = new Titoli();
            retVal.CODICETITOLO = pCODICETITOLO;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public Titoli GetByDescrizione(String pTITOLO, String pIDCOMUNE)
        {
            Titoli retVal = new Titoli();
            retVal.TITOLO = pTITOLO;
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
        public List<Titoli> GetList(Titoli p_class)
        {
            return this.db.GetClassList(p_class).ToList<Titoli>();
        }

        #endregion
    }
}
