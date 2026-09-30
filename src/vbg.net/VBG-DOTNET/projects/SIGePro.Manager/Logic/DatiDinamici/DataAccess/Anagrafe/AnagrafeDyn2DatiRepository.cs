using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Anagrafe
{
    public class AnagrafeDyn2DatiRepository : IDyn2DatiRepository
    {
        protected readonly int _idAnagrafe;
        protected readonly string _idComune;
        private readonly DataBase _database;

        public AnagrafeDyn2DatiRepository(DataBase database, int idAnagrafe, string idComune)
        {
            this._idAnagrafe = idAnagrafe;
            this._idComune = idComune;
            this._database = database;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            AnagrafeDyn2DatiMgr manager = this.GetManager();
            manager.EliminaValoriCampi(this._idComune, this._idAnagrafe, modello, campiDaEliminare);
        }

        private AnagrafeDyn2DatiMgr GetManager()
        {
            return new AnagrafeDyn2DatiMgr(this._database);
        }

        public virtual SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var manager = this.GetManager();
            var valori = manager.GetValoriCampiDaIdModello(this._idComune, this._idAnagrafe, idModello, indiceModello);

            return valori.GroupBy(x => x.FkD2cId.Value).ToSerializableDictionary(x => x.Key, y => y.Cast<IValoreCampo>());
        }

        public virtual void SalvaValoriCampi(DatiIdentificativiModello modello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            var mgr = this.GetManager();
            mgr.SalvaValoriCampi(this._idComune, this._idAnagrafe, modello, campiDaSalvare);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
            // throw new NotImplementedException();
        }
    }
}
