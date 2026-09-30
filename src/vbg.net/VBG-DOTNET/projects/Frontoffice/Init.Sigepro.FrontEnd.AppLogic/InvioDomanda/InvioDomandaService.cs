using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.RiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System.Threading.Tasks;


namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    public enum TipoInvioEnum
    {
        Firma,
        Sottoscrizione
    }


    public class InvioDomandaAreaRiservataService : IInvioDomandaService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly WorkflowInvioDomanda _workflowInvioDomanda;
        private readonly RiepilogoDomandaAllegatoService _riepilogoDomandaService;
        private readonly ILog _log = LogManager.GetLogger(typeof(InvioDomandaAreaRiservataService));

        public InvioDomandaAreaRiservataService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, WorkflowInvioDomanda workflowInvioDomanda, RiepilogoDomandaAllegatoService riepilogoDomandaService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._workflowInvioDomanda = workflowInvioDomanda;
            this._riepilogoDomandaService = riepilogoDomandaService;
        }


#if NET48
        public InvioIstanzaResult? Invia(int idPresentazione, string pecDestinatario)
        {
            try
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(idPresentazione);

                var result = this._workflowInvioDomanda.Processa(domanda, pecDestinatario);

                if (result.Esito != InvioIstanzaResult.TipoEsitoInvio.ErroreInvio)
                {
                    domanda.ImpostaComePresentata();

                    this._log.Info($"La domanda con id {idPresentazione} è stata impostata come presentata ma non è stata ancora salvata");
                }
                this._salvataggioDomandaStrategy.Salva(domanda);

                this._log.Info($"La domanda con id {idPresentazione} è stata salvata correttamente");

                return result;
            }
            catch (System.Exception ex)
            {
                this._log.Error($"Errore durante l'invio della domanda con id {idPresentazione}: {ex.Message}");
                return null;
            }

        }
#endif

        public Task<ValidazioneIstanzaResult> ValidaDomandaAsync(int idDomanda) => Task.FromResult(ValidazioneIstanzaResult.Success());

        public async Task<InvioIstanzaResult> InviaDomandaAsync(int idDomanda, ParametriInvioDomanda parametriInvio)
        {
            try
            {
                if (parametriInvio.GeneraEAllegaRiepilogoDomanda)
                {
                    await this._riepilogoDomandaService.RigeneraRiepilogoDomandaAsync(idDomanda);
                }

                var domanda = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

                this._log.Info($"Inizio l'invio della domanda {domanda.DataKey} tramite STC");

                if (domanda.ReadInterface.IsPresentata())
                {
                    return InvioIstanzaResult.IstanzaGiaPresentata();
                }

                var result = await this._workflowInvioDomanda.ProcessaAsync(domanda, parametriInvio);

                if (result.Esito != InvioIstanzaResult.TipoEsitoInvio.ErroreInvio)
                {
                    this._log.Info($"Marco la domanda {domanda.DataKey} come presentata");

                    domanda.ImpostaComePresentata();
                }

                await this._salvataggioDomandaStrategy.SalvaAsync(domanda);

                return result;
            }
            catch (System.Exception ex)
            {
                this._log.Error($"Errore durante l'invio della domanda con id {idDomanda}: {ex.Message}", ex);
                return InvioIstanzaResult.ErroreInvio();
            }
        }
    }
}
