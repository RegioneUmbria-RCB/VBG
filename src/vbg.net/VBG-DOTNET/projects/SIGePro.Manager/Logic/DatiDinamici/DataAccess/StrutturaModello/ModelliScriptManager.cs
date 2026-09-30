using PersonalLib2.Data;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class ModelliScriptManager : IDyn2ScriptModelloManager
    {
        private readonly string _idComune;
        private readonly Dyn2ModelliScriptMgr _manager;

        public ModelliScriptManager(DataBase dataBase, string idComune)
        {
            this._idComune = idComune;
            this._manager = new Dyn2ModelliScriptMgr(dataBase);
        }

        public IDyn2ScriptModello GetById(int idModello, TipoScriptEnum contesto) => this._manager.GetScriptById(this._idComune, idModello, contesto);
    }
}
