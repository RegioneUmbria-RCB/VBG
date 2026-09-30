using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per ParametriEsportazioneMgr.
    /// </summary>
    public class ParametriEsportazioneMgr : BaseManager
    {
        public ParametriEsportazioneMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public PARAMETRIESPORTAZIONE GetById(String pIDCOMUNE, String pID)
        {
            PARAMETRIESPORTAZIONE retVal = new PARAMETRIESPORTAZIONE
            {
                IDCOMUNE = pIDCOMUNE,
                ID = pID
            };

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public PARAMETRIESPORTAZIONE GetByClass(PARAMETRIESPORTAZIONE p_class)
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
        public List<PARAMETRIESPORTAZIONE> GetList(PARAMETRIESPORTAZIONE p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<PARAMETRIESPORTAZIONE> GetList(PARAMETRIESPORTAZIONE p_class, PARAMETRIESPORTAZIONE p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass);
        }


        public PARAMETRIESPORTAZIONE Insert(PARAMETRIESPORTAZIONE p_class)
        {
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (this.findMax("PARAMETRIESPORTAZIONE", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

            this.db.Insert(p_class);
            return p_class;
        }

        public PARAMETRIESPORTAZIONE Delete(PARAMETRIESPORTAZIONE p_class)
        {
            this.db.Delete(p_class);
            return p_class;
        }

        public PARAMETRIESPORTAZIONE Update(PARAMETRIESPORTAZIONE p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }

        #endregion
    }
}
