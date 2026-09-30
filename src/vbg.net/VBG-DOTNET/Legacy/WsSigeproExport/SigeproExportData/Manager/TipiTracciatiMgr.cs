using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class TipiTracciatiMgr : BaseManager
    {
        public TipiTracciatiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TIPITRACCIATI GetById(String pID)
        {
            TIPITRACCIATI retVal = new TIPITRACCIATI
            {
                CODICETIPO = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public TIPITRACCIATI GetByClass(TIPITRACCIATI p_class)
        {
            var mydc = this.db.GetClassList(p_class, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TIPITRACCIATI> GetList(TIPITRACCIATI p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<TIPITRACCIATI> GetList(TIPITRACCIATI p_class, TIPITRACCIATI p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass);
        }
        #endregion

        //
        public TIPITRACCIATI Update(TIPITRACCIATI p_class)
        {
            this.db.Update(p_class);

            return p_class;
        }
    }
}

