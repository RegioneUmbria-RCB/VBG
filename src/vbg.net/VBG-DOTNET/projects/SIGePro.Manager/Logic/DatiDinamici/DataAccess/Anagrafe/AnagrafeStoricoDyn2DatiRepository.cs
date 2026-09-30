using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Anagrafe
{
    public class AnagrafeStoricoDyn2DatiRepository : IDyn2DatiStoricoRepository
    {
        private readonly int _idVersioneStorico;
        private readonly int _idAnagrafe;
        private readonly string _idComune;
        private readonly DataBase _database;

        public AnagrafeStoricoDyn2DatiRepository(DataBase database, int idAnagrafe, string idComune, int idVersioneStorico)
        {
            _idVersioneStorico = idVersioneStorico;
            _idAnagrafe = idAnagrafe;
            _idComune = idComune;
            _database = database;
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            AnagrafeDyn2DatiStoricoMgr manager = GetManager();
            var valori = manager.GetValoriCampiDaIdModello(this._idComune, this._idAnagrafe, idModello, indiceModello, this._idVersioneStorico);

            return valori.GroupBy(x => x.FkD2cId.Value).ToSerializableDictionary(x => x.Key, y => y.Cast<IValoreCampo>());
        }

        private AnagrafeDyn2DatiStoricoMgr GetManager()
        {
            return new AnagrafeDyn2DatiStoricoMgr(this._database);
        }

        public void SalvaStoricoModello(ModelloDinamicoBase modelloDinamicoBase)
        {
            var manager = GetManager();

            manager.SalvaStoricoModello(this._idComune, this._idAnagrafe, modelloDinamicoBase);
        }
    }
}
