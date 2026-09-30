using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda
{
    public class ModelloDomandaReaderFactory
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IOggettiService _oggettiService;

        public ModelloDomandaReaderFactory(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IOggettiService oggettiService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._oggettiService = oggettiService;
        }

        public async Task<IModelloDomandaReader> CreateAsync(int idDomanda)
        {
            var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);
            var oggettoRiepilogo = domanda.ReadInterface.Documenti.Intervento.GetRiepilogoDomanda();

            if (oggettoRiepilogo == null || !oggettoRiepilogo.CodiceOggettoModello.HasValue)
            {
                throw new Exception("Non è stato definito un riepilogo di domanda per la domanda con id " + idDomanda);
            }

            int idFileModello = oggettoRiepilogo.CodiceOggettoModello.Value;
            var oggetto = await this._oggettiService.GetByIdAsync(idFileModello);

            if (oggetto == null)
                throw new ArgumentException("L'oggetto " + idFileModello + " non è stato trovato");

            return new ModelloDomandaDaBinaryFileReader(oggetto);
        }
    }
}
