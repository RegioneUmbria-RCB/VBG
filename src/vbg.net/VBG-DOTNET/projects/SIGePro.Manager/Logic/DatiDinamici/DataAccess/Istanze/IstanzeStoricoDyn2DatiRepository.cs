using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze
{
    public class IstanzeStoricoDyn2DatiRepository : IDyn2DatiStoricoRepository
    {
        private readonly int _idVersioneStorico;
        private readonly DataBase _database;
        private readonly string _idComune;
        private readonly int _codiceIstanza;

        public IstanzeStoricoDyn2DatiRepository(DataBase database, string idComune, int codiceIstanza, int idVersioneStorico)
        {
            this._idVersioneStorico = idVersioneStorico;
            this._database = database;
            this._idComune = idComune;
            this._codiceIstanza = codiceIstanza;
        }

        private IstanzeDyn2DatiStoricoMgr GetManager()
        {
            return new IstanzeDyn2DatiStoricoMgr(this._database);
        }

        public void SalvaStoricoModello(ModelloDinamicoBase modelloDinamicoBase)
        {
            var manager = this.GetManager();

            manager.SalvaStoricoModello(this._idComune, this._codiceIstanza, modelloDinamicoBase);
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var manager = this.GetManager();
            var listaValori = manager.GetValoriCampiDaIdModello(this._idComune, this._codiceIstanza, idModello, indiceModello, this._idVersioneStorico);

            return listaValori
                        .GroupBy(x => x.FkD2cId.Value)
                        .ToSerializableDictionary(x => x.Key, y => y.Cast<IValoreCampo>());
        }
    }
}
