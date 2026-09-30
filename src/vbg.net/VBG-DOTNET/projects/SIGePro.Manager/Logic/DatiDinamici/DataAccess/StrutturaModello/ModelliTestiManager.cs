using PersonalLib2.Data;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello
{
    public class ModelliTestiManager : IDyn2TestoModelloManager
    {
        private readonly string _idComune;
        private readonly Dyn2ModelliDTestiMgr _manager;

        public ModelliTestiManager(DataBase db, string idComune)
        {
            this._idComune = idComune;
            this._manager = new Dyn2ModelliDTestiMgr(db);
        }

        public SerializableDictionary<int, IDyn2TestoModello> GetListaTestiDaIdModello(int idModello)
        {
            var testi = this._manager.GetTestiDtoByIdModello(this._idComune, idModello);

            if (testi == null)
            {
                return new SerializableDictionary<int, IDyn2TestoModello>();
            }

            return new SerializableDictionary<int, IDyn2TestoModello>(testi.ToDictionary(x => x.Id.Value, x => (IDyn2TestoModello)x));
        }
    }
}
