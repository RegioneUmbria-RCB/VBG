using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Scripts;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class CampiScriptManager : IDyn2ScriptCampiManager
    {
        public string _idComune;
        public Dyn2CampiScriptMgr _manager;

        public CampiScriptManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._manager = new Dyn2CampiScriptMgr(db);
        }

        public Dictionary<TipoScriptEnum, IDyn2ScriptCampo> GetScriptsCampo(int idCampo) => this._manager.GetScriptsCampo(this._idComune, idCampo);
    }
}
