using PersonalLib2.Data;
using System.Collections.Generic;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class CampiProprietaManager : IDyn2ProprietaCampiManager
    {
        public string _idComune;
        public Dyn2CampiProprietaMgr _manager;

        public CampiProprietaManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._manager = new Dyn2CampiProprietaMgr(db);
        }

        public List<IDyn2ProprietaCampo> GetProprietaCampo(int idCampo) => this._manager.GetProprietaCampo(this._idComune, idCampo);
    }
}
