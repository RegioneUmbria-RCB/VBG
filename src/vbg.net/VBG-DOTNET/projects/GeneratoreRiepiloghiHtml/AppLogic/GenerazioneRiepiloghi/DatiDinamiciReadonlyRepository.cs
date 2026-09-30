using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede
{
    public class DatiDinamiciReadonlyRepository : IDyn2DatiRepository
    {
        private readonly Dictionary<int, IEnumerable<IValoreCampo>> _valoriCampi = new();

        public DatiDinamiciReadonlyRepository(Dictionary<int, List<IValoreCampo>> valoriCampi)
        {
            foreach (var key in valoriCampi)
            {
                this._valoriCampi[key.Key] = key.Value.AsEnumerable();
            }
        }

        public void EliminaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            throw new NotImplementedException();
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            return new SerializableDictionary<int, IEnumerable<IValoreCampo>>(this._valoriCampi);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> enumerable)
        {
            throw new NotImplementedException();
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            throw new NotImplementedException();
        }
    }
}
