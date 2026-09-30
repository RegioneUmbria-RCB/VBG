using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Options;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneGenerazioneRicevuta;
using VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione;
using VBG.AppLogic.SSU.GeneratoreRicevute.Hubs;
using VBG.AppLogic.SSU.HubInterfaces;


namespace VBG.AppLogic.SSU.GeneratoreRicevute.GenerazioneRicevuta
{
    public class GenerazioneRicevutaService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IDomandeSsuRepository _domandeSsuRepository;
        private readonly GeneraRicevutaService _generazioneRicevutaService;
        private readonly IOptions<GeneratoreRicevuteOptions> _options;
        private readonly ILogger<GenerazioneRicevutaService> _logger;
        private readonly NotificaStcService _notificaStcService;
        private readonly InvioEmailService _invioEmailService;
        private readonly IHubContext<DomandeNotificationHub, IDomandeNotificationClient> _hubContext;


        public GenerazioneRicevutaService(
            SalvataggioDirettoStrategy salvataggioDomandaStrategy,
            IDomandeSsuRepository domandeSsuRepository,
            GeneraRicevutaService generazioneRicevutaService,
            IOptions<GeneratoreRicevuteOptions> options,
            ILogger<GenerazioneRicevutaService> logger,
            NotificaStcService notificaStcService,
            InvioEmailService invioEmailService,
            IHubContext<DomandeNotificationHub, IDomandeNotificationClient> hubContext)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._domandeSsuRepository = domandeSsuRepository;
            this._generazioneRicevutaService = generazioneRicevutaService;
            this._options = options;
            this._logger = logger;
            this._notificaStcService = notificaStcService;
            this._invioEmailService = invioEmailService;
            this._hubContext = hubContext;
        }

        public async Task ElaboraPraticaAsync(int idDomanda)
        {
            var domandaSsu = this._domandeSsuRepository.GetByIdDomanda(idDomanda);
            var domandaAr = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

            try
            {
                await this.ElaboraPraticaInternalAsync(domandaAr, domandaSsu!);
            }
            catch (Exception ex)
            {
                this._logger.LogError(
                    "Errore durante l'esecuzione del metodo ElaboraPraticaInternalAsync per la domanda SSU con CUI {0}, relativa alla domanda {1} ed avente stato {2}({3}): {4}",
                    domandaSsu.IdDomandaSsu, domandaSsu.FkIdDomanda, domandaSsu.StatoAsEnum, ((int)domandaSsu.StatoAsEnum), ex);

                if (this._domandeSsuRepository.AddNewError(
                    domandaSsu.FkIdDomanda,
                    (StatiDomandaSsuEnum)domandaSsu.Stato,
                    ex.Message))
                {
                    await this._hubContext.Clients.All.SendNuovoErroreDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda);

                    var count = this._domandeSsuRepository.CountErroriDomandaPerStato(
                        domandaSsu.FkIdDomanda,
                        domandaSsu.StatoAsEnum);

                    if (count % this._options.Value.MaxFailedAttemptsThreshold == 0)
                    {
                        this._logger.LogWarning("La domanda SSU relativa alla domanda {IdDomandaSsu} ha raggiunto {Count} errori per lo stato {Stato}({num}). Imposto come Non Elaborabile.",
                            domandaSsu.FkIdDomanda, count, domandaSsu.Stato, ((int)domandaSsu.Stato));

                        this._domandeSsuRepository.SetNonElaborabile(domandaSsu.FkIdDomanda, false);

                        await this._hubContext.Clients.All.SendDomandaElaborabileAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, false);
                    }
                }
                else
                {
                    this._logger.LogError(ex, "Impossibile registrare l'errore per la domanda SSU relativa alla domanda {IdDomandaSsu}", domandaSsu.FkIdDomanda);
                }
            }
        }

        private async Task ElaboraPraticaInternalAsync(DomandaOnline domandaAr, FoDomandeSsu domandaSsu)
        {
            switch (domandaSsu.StatoAsEnum)
            {
                case StatiDomandaSsuEnum.Inviata:
                    {
                        await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, -1, (int)StatiDomandaSsuEnum.Inviata);
                        this._logger.LogDebug("Generazione ricevuta per la domanda SSU con idDomanda={IdDomanda}", domandaSsu.FkIdDomanda);

                        var codiceOggettoRicevuta = await this._generazioneRicevutaService.GeneraPdfRicevutaAsync(domandaAr, domandaSsu.IdDomandaSsu);

                        this._domandeSsuRepository.AggiungiRicevutaByIdDomanda(domandaSsu.FkIdDomanda, codiceOggettoRicevuta);
                        this._domandeSsuRepository.AggiornaStatoByIdDomanda(domandaSsu.FkIdDomanda, StatiDomandaSsuEnum.RicevutaGenerata);

                        await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, (int)StatiDomandaSsuEnum.Inviata, (int)StatiDomandaSsuEnum.RicevutaGenerata);

                        await this.ElaboraPraticaAsync(domandaSsu.FkIdDomanda);
                        break;
                    }
                case StatiDomandaSsuEnum.RicevutaGenerata:
                    {
                        await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, (int)StatiDomandaSsuEnum.Inviata, (int)StatiDomandaSsuEnum.RicevutaGenerata);
                        this._logger.LogDebug("Notifica ricevuta a STC per la domanda SSU con idDomanda={IdDomanda}", domandaSsu.FkIdDomanda);

                        var notificaStcCompletata = this._notificaStcService.NotificaAttivitaAStc(domandaAr, domandaSsu);

                        if (notificaStcCompletata)
                        {
                            this._domandeSsuRepository.AggiornaStatoByIdDomanda(domandaSsu.FkIdDomanda, StatiDomandaSsuEnum.RicevutaNotificataAStc);

                            await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, (int)StatiDomandaSsuEnum.RicevutaGenerata, (int)StatiDomandaSsuEnum.RicevutaNotificataAStc);

                            await this.ElaboraPraticaAsync(domandaSsu.FkIdDomanda);
                        }

                        break;
                    }
                case StatiDomandaSsuEnum.RicevutaNotificataAStc:
                    {
                        await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, (int)StatiDomandaSsuEnum.RicevutaGenerata, (int)StatiDomandaSsuEnum.RicevutaNotificataAStc);
                        this._logger.LogDebug("Notifica ricevuta all'utente per la domanda SSU con idDomanda={IdDomanda}", domandaSsu.FkIdDomanda);

                        var invioEmailCompletato = await this._invioEmailService.InviaEmailAUtenteAsync(domandaAr, domandaSsu);

                        if (invioEmailCompletato)
                        {
                            this._domandeSsuRepository.AggiornaStatoByIdDomanda(domandaSsu.FkIdDomanda, StatiDomandaSsuEnum.RicevutaNotificataAUtente);

                            await this._hubContext.Clients.All.SendNuovoStatoDomandaAsync(domandaSsu.IdComune, domandaSsu.FkIdDomanda, (int)StatiDomandaSsuEnum.RicevutaNotificataAStc, (int)StatiDomandaSsuEnum.RicevutaNotificataAUtente);

                            await this.ElaboraPraticaAsync(domandaSsu.FkIdDomanda);
                        }

                        break;
                    }
                default:
                    {
                        this._logger.LogDebug("Nessuna azione");
                        // L'elaborazione è stata completata, non c'è altro da fare
                        break;
                    }
            }
        }
    }
}
