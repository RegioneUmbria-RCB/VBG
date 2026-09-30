using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze
{
    public class IstanzeDyn2DatiRepository : IDyn2DatiRepository
    {
        //protected readonly IDyn2DataAccessProvider _dataAccessProvider;
        protected readonly int _codiceIstanza;
        protected readonly string _idComune;
        private readonly DataBase _database;

        public IstanzeDyn2DatiRepository(DataBase database, int codiceIstanza, string idComune)
        {
            this._codiceIstanza = codiceIstanza;
            this._idComune = idComune;
            this._database = database;
        }

        private IstanzeDyn2DatiMgr CreateManager()
        {
            return new IstanzeDyn2DatiMgr(this._database);
        }

        public void EliminaValoriCampi(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            var mgr = this.CreateManager();
            mgr.EliminaValoriCampi(this._idComune, this._codiceIstanza, modello, campiDaEliminare);
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            return this.GetValori(idModello, indiceModello);
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            var mgr = this.CreateManager();

            mgr.SalvaValoriCampi(this._idComune, this._codiceIstanza, idModello, campiDaSalvare);
        }

        protected virtual SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValori(int idModello, int indiceModello)
        {
            var manager = this.CreateManager();
            var result = manager.GetValoriCampiDaIdModello(this._idComune, this._codiceIstanza, idModello, indiceModello);

            var rVal = new SerializableDictionary<int, IEnumerable<IValoreCampo>>();

            foreach (var key in result.Keys)
            {
                rVal[key] = result[key].Select(x => x);
            }

            return rVal;
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
        }
    }
}
