using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per ResponsabiliSoftwareMgr.\n	/// </summary>
    public class ResponsabiliSoftwareMgr : BaseManager
    {

        public ResponsabiliSoftwareMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB
        public ResponsabiliSoftware GetById(string idComune, int codiceResponsabile, string software)
        {
            ResponsabiliSoftware filtro = new ResponsabiliSoftware();
            filtro.IDCOMUNE = idComune;
            filtro.CODICERESPONSABILE = codiceResponsabile.ToString();
            filtro.SOFTWARE = software;

            return (ResponsabiliSoftware)this.db.GetClass(filtro);
        }

        public ResponsabiliSoftware GetById(String pCODICERESPONSABILE, String pSOFTWARE, String pIDCOMUNE)
        {
            ResponsabiliSoftware retVal = new ResponsabiliSoftware();
            retVal.CODICERESPONSABILE = pCODICERESPONSABILE;
            retVal.SOFTWARE = pSOFTWARE;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<ResponsabiliSoftware> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as ResponsabiliSoftware;

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<ResponsabiliSoftware> GetList(ResponsabiliSoftware p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<ResponsabiliSoftware> GetList(ResponsabiliSoftware p_class, ResponsabiliSoftware p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false);
        }

        #endregion
    }
}