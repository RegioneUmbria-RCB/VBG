using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda
{
    public class RiepilogoDomandaAllegatoLegacyService
    {
        private readonly GenerazioneRiepilogoDomandaLegacyService _generazioneriepilogoService;
        private readonly RiepilogoDomandaAllegatoService _riepilogoDomandaService;

        public RiepilogoDomandaAllegatoLegacyService(GenerazioneRiepilogoDomandaLegacyService generazioneriepilogoService, RiepilogoDomandaAllegatoService riepilogoDomandaService)
        {
            this._generazioneriepilogoService = generazioneriepilogoService;
            this._riepilogoDomandaService = riepilogoDomandaService;
        }

        public void RigeneraRiepilogoDomanda(int idDomanda)
        {

            this._riepilogoDomandaService.EliminaOggettoRiepilogoDomanda(idDomanda);

            var riepilogo = this._generazioneriepilogoService.GeneraRiepilogoDomanda(idDomanda, true, false);

            this._riepilogoDomandaService.SalvaOggettoRiepilogo(idDomanda, riepilogo);

        }
    }
}
