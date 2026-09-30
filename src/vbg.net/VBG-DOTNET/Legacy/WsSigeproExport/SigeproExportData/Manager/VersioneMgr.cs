using Init.SIGeProExport.Data;
using PersonalLib2.Data;

namespace Init.SIGeProExport.Manager
{
    public class VersioneMgr : BaseManager
    {
        public VersioneMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public string GetVersione()
        {
            var filtro = new VERSIONE();
            var mydc = this.db.GetClassList(filtro, true);

            if (mydc.Count != 0)
                return mydc[0].Versione;

            return null;
        }

        #endregion
    }
}
