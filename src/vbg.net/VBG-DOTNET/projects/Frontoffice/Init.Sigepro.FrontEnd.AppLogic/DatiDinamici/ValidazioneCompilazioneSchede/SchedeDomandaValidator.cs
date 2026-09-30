using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.ValidazioneCompilazioneSchede
{
    public class SchedeDomandaValidator
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public SchedeDomandaValidator(ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public async Task<ErroriSalvataggioSchedeDomanda> VerificaErroriSchedeDomandaAsync(int idDomanda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);
            var result = new ErroriSalvataggioSchedeDomanda();

            // Trovo i modelli obbligatori ma non compilati
            var modelliObbligaotriNonCompilati = domanda.ReadInterface.DatiDinamici.Modelli.Where(x => !x.Facoltativo && !x.Compilato);

            if (modelliObbligaotriNonCompilati.Any())
            {
                foreach (var modello in modelliObbligaotriNonCompilati)
                {
                    result.AddError(new SchedaNonCompilataError(modello.IdModello));
                }
            }

            throw new NotImplementedException();
        }
    }
}
