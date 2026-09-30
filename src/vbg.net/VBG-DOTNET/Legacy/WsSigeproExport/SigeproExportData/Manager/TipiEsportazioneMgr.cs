using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class TipiEsportazioneMgr : BaseManager
    {
        public TipiEsportazioneMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TIPIESPORTAZIONE GetById(String pID)
        {
            TIPIESPORTAZIONE retVal = new TIPIESPORTAZIONE
            {
                CODICETIPO = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public TIPIESPORTAZIONE GetByClass(TIPIESPORTAZIONE p_class)
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
        public List<TIPIESPORTAZIONE> GetList(TIPIESPORTAZIONE p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<TIPIESPORTAZIONE> GetList(TIPIESPORTAZIONE p_class, TIPIESPORTAZIONE p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass);
        }
        #endregion

        //
        public TIPIESPORTAZIONE Update(TIPIESPORTAZIONE p_class)
        {
            this.db.Update(p_class);

            return p_class;
        }
    }
}
