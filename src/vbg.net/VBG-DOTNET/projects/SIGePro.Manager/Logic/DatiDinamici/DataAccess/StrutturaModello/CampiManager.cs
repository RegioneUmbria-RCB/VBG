using PersonalLib2.Data;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class CampiManager : IDyn2CampiManager
    {
        private readonly string _idComune;
        private readonly Dyn2CampiMgr _mgr;

        public CampiManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._mgr = new Dyn2CampiMgr(db);
        }

        public IDyn2Campo GetById(int idCampo) => this._mgr.GetById(this._idComune, idCampo);

        public int? GetIdCampoDaNome(string software, string nomeCampo)
        {
            return this._mgr.GetIdCampoDaNome(this._idComune, software, nomeCampo);
        }

        public SerializableDictionary<int, IDyn2Campo> GetListaCampiDaIdModello(int idModello)
        {
            var campi = this._mgr.GetCampiDinamiciByIdModello(this._idComune, idModello);

            if (campi == null)
            {
                return new SerializableDictionary<int, IDyn2Campo>();
            }

            return campi.ToSerializableDictionary(x => x.Id.Value, x => (IDyn2Campo)x);
        }
    }
}
