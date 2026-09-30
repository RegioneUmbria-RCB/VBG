using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Attivita
{
    public class AttivitaDyn2DatiRepository : IDyn2DatiRepository
    {
        protected readonly int _idAttivita;
        private readonly ISchedeDinamicheAttivitaService _schedeDinamicheAttivitaService;

        public AttivitaDyn2DatiRepository(ISchedeDinamicheAttivitaService schedeDinamicheAttivitaService, int idAttivita)
        {
            this._idAttivita = idAttivita;
            this._schedeDinamicheAttivitaService = schedeDinamicheAttivitaService;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello modello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            this._schedeDinamicheAttivitaService.EliminaValoriCampi(this._idAttivita, modello, campiDaEliminare);
        }

        public virtual SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var valori = this._schedeDinamicheAttivitaService.GetValoriCampiDaIdModello(this._idAttivita, idModello, indiceModello);

            return valori.GroupBy(x => x.FkD2cId.Value).ToSerializableDictionary(x => x.Key, y => y.Cast<IValoreCampo>());
        }

        public virtual void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            this._schedeDinamicheAttivitaService.SalvaValoriCampi(this._idAttivita, idModello, campiDaSalvare);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
        }
    }
}
