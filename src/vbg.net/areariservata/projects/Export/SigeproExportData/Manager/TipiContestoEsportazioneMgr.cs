using System;
using System.Collections;
using System.Data;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Init.Utils;

namespace Init.SIGeProExport.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per EsportazioniMgr.
    /// </summary>
    public class TipiContestoEsportazioneMgr : BaseManager
    {
        public TipiContestoEsportazioneMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public TIPICONTESTOESPORTAZIONE GetById(String pID)
        {
            TIPICONTESTOESPORTAZIONE retVal = new TIPICONTESTOESPORTAZIONE();
            retVal.CODICE = pID;

            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as TIPICONTESTOESPORTAZIONE;

            return null;
        }

        public TIPICONTESTOESPORTAZIONE GetByClass(TIPICONTESTOESPORTAZIONE p_class)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as TIPICONTESTOESPORTAZIONE;

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public ArrayList GetList(TIPICONTESTOESPORTAZIONE p_class)
        {
            return this.GetList(p_class, null);
        }

        public ArrayList GetList(TIPICONTESTOESPORTAZIONE p_class, TIPICONTESTOESPORTAZIONE p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false);
        }
        #endregion

        //
        public TIPICONTESTOESPORTAZIONE Update(TIPICONTESTOESPORTAZIONE p_class)
        {
            db.Update(p_class);

            return p_class;
        }
    }
}