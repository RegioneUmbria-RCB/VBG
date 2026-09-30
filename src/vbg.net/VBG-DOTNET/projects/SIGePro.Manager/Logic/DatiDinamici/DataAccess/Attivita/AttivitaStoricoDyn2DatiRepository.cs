using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Authentication;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Attivita
{
    public class AttivitaStoricoDyn2DatiRepository : IDyn2DatiStoricoRepository
    {
        private readonly int _idVersioneStorico;
        private readonly int _idAttivita;
        private readonly ISchedeDinamicheAttivitaService _schedeDinamicheAttivitaService;

        public AttivitaStoricoDyn2DatiRepository(ISchedeDinamicheAttivitaService schedeDinamicheAttivitaService, int idAttivita, int idVersioneStorico)
        {
            _idVersioneStorico = idVersioneStorico;
            _schedeDinamicheAttivitaService = schedeDinamicheAttivitaService;
            _idAttivita = idAttivita;
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            // IAttivitaDyn2DatiStoricoMgr manager = GetManager();
            var valori = _schedeDinamicheAttivitaService.GetValoriCampiDaIdModelloStorico(this._idAttivita, idModello, indiceModello, this._idVersioneStorico);

            return valori.GroupBy(x => x.FkD2cId.Value).ToSerializableDictionary(x => x.Key, y => y.Cast<IValoreCampo>());
        }

        // private IAttivitaDyn2DatiStoricoMgr GetManager()
        // {
        //     return new IAttivitaDyn2DatiStoricoMgr(this._database);
        // }

        public void SalvaStoricoModello(ModelloDinamicoBase modelloDinamicoBase)
        {
            // var manager = GetManager();
            _schedeDinamicheAttivitaService.SalvaStoricoModello(this._idAttivita, modelloDinamicoBase);
        }
    }
}
