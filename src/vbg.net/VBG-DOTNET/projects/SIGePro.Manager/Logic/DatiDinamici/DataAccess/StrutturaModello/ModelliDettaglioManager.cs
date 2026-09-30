using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class ModelliDettaglioManager : IDyn2DettagliModelloManager
    {
        private readonly string _idComune;
        private readonly Dyn2ModelliDMgr _manager;

        public ModelliDettaglioManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._manager = new Dyn2ModelliDMgr(db);
        }

        public List<IDyn2DettagliModello> GetList(int idModello) => this._manager.GetListByIdModello(this._idComune, idModello).Cast<IDyn2DettagliModello>().ToList();
    }
}
