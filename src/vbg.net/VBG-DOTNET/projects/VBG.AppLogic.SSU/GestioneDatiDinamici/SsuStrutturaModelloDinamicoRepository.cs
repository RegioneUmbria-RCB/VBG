using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace VBG.AppLogic.SSU.GestioneDatiDinamici
{
    public class SsuStrutturaModelloDinamicoRepository : IStrutturaModelloDinamicoRepository
    {
        private readonly IEnumerable<SchedaDinamica> _schedeDinamiche;

        public SsuStrutturaModelloDinamicoRepository(IEnumerable<SchedaDinamica> schedeDinamiche)
        {
            this._schedeDinamiche = schedeDinamiche;
        }

        public IStrutturaModelloDinamico GetStrutturaModelloDinamico(int idModello)
        {
            var scheda = this._schedeDinamiche.FirstOrDefault(x => x.Id == idModello);

            if (scheda == null)
            {
                throw new KeyNotFoundException($"Impossibile trovare la scheda dinamica con id {idModello}");
            }

            return scheda.ToStrutturaModelloDinamico();
        }
    }
}
