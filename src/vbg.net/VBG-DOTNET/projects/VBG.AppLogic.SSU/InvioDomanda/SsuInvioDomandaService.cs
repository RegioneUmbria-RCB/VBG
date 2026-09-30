using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.GestioneCorrezioni;
using VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni;
using VBG.AppLogic.SSU.MapperValidator;
using VBG.AppLogic.SSU.OperazioniPostInvio;


namespace VBG.AppLogic.SSU.InvioDomanda
{
    public class SsuInvioDomandaService : IInvioDomandaService
    {
        private readonly SsuValidatorService _validatorService;
        private readonly SsuOperazioniPostInvioService _operazioniPostInvio;
        private readonly InvioDomandaAreaRiservataService _invioDomandaAreaRiservataService;
        private readonly IGestioneCorrezioniSsuService _gestioneCorrezioniSsuService;
        private readonly IGestioneRichiestaIntegrazioniService _gestioneRichiestaIntegrazioniService;
        private readonly ILogger<SsuInvioDomandaService> _logger;
        private readonly NotificaCorrezioneStcService _notificaCorrezioneStcService;
        private readonly IDomandeSsuRepository _domandeSsuRepository;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public SsuInvioDomandaService(SsuValidatorService validatorService, SsuOperazioniPostInvioService operazioniPostInvio, InvioDomandaAreaRiservataService invioDomandaAreaRiservataService, IGestioneCorrezioniSsuService gestioneCorrezioniSsuService, IGestioneRichiestaIntegrazioniService gestioneRichiestaIntegrazioniService, ILogger<SsuInvioDomandaService> logger, NotificaCorrezioneStcService notificaCorrezioneStcService, IDomandeSsuRepository domandeSsuRepository, ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._validatorService = validatorService;
            this._operazioniPostInvio = operazioniPostInvio;
            this._invioDomandaAreaRiservataService = invioDomandaAreaRiservataService;
            this._gestioneCorrezioniSsuService = gestioneCorrezioniSsuService;
            this._gestioneRichiestaIntegrazioniService = gestioneRichiestaIntegrazioniService;
            this._logger = logger;
            this._notificaCorrezioneStcService = notificaCorrezioneStcService;
            this._domandeSsuRepository = domandeSsuRepository;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }


        public async Task<InvioIstanzaResult> InviaDomandaAsync(int idDomanda, ParametriInvioDomanda parametriInvio)
        {
            // TODO:
            // - Se è una nuova pratica (non ha record su FO_DOMANDE_SSU) allora va effettuato un invia domande normale
            // - Se è una richiesta di correzione la pratica va inviata come movimento (notifica attività). La richiesta in formato STC va allegata come documento alla pratica
            // - Se è una richiesta di integrazione la pratica va inviata come movimento (notifica attività) che contiene come unico allegato l'xml della domanda modificato dall'utente


            // Se è una richiesta di correzione (IGestioneCorrezioniSsuService.DomandaDaCorreggere(idDomanda) == true)
            // - Convertire domanda in formato stc (iniettare IIstanzaStcAdapter e usare Adatta per generare la classe, poi serializzarla in xml)
            // - Salvare l'xml della domanda come documento allegato alla pratica (Iniettare IOggettiService)
            // - Inviare notifica attività ad stc (IStcService.NotificaAttivita )

            try
            {
                if (this._gestioneCorrezioniSsuService.DomandaDaCorreggere(idDomanda))
                {
                    // se è una richiesta di correzione
                    var domandaSsu = this._domandeSsuRepository.GetByIdDomanda(idDomanda);
                    var domandaAr = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

                    var notificaStcCompletata = await this._notificaCorrezioneStcService.NotificaCorrezioneByIdDomandaAsync(domandaAr);

                    if (notificaStcCompletata)
                    {
                        this._operazioniPostInvio.MarcaDomandaComeCorretta(idDomanda);
                        return InvioIstanzaResult.InvioRiuscito(domandaSsu?.IdDomandaSsu ?? "", domandaSsu?.NumeroDomandaSsu ?? "", null, null);
                    }
                    else
                    {
                        return InvioIstanzaResult.InvioFallito();
                    }
                }
                else if (this._gestioneRichiestaIntegrazioniService.DomandaDaIntegrare(idDomanda))
                {
                    // se è una richiesta di integrazione
                    var domandaSsu = this._domandeSsuRepository.GetByIdDomanda(idDomanda);
                    var domandaAr = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);

                    var notificaStcCompletata = await this._notificaCorrezioneStcService.NotificaIntegrazioneByIdDomandaAsync(domandaAr);

                    if (notificaStcCompletata)
                    {
                        this._gestioneRichiestaIntegrazioniService.MarcaDomandaComeIntegrata(idDomanda);
                        return InvioIstanzaResult.InvioRiuscito(domandaSsu?.IdDomandaSsu ?? "", domandaSsu?.NumeroDomandaSsu ?? "", null, null);
                    }
                    else
                    {
                        return InvioIstanzaResult.InvioFallito();
                    }
                }
                else
                {
                    // Nuova Domanda
                    var result = await this.InviaNuovaPraticaAsync(idDomanda, parametriInvio);
                    return result;
                }
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante l'esecuzione del metodo InviaDomandaAsync con idDomanda {IdDomanda}: {ex}", idDomanda, ex);
                throw;
            }
        }

        private async Task<InvioIstanzaResult> InviaNuovaPraticaAsync(int idDomanda, ParametriInvioDomanda parametriInvio)
        {
            var result = await this._invioDomandaAreaRiservataService.InviaDomandaAsync(idDomanda, parametriInvio);

            if (result.IsSuccess())
            {
                var domandaAr = await this._salvataggioDomandaStrategy.GetByIdAsync(idDomanda);
                this._operazioniPostInvio.MarcaDomandaComePresentata(idDomanda, result.CodiceIstanza, result.NumeroIstanza, domandaAr.ReadInterface.Ssu.CodiceEnte);
            }

            return result;
        }

        public async Task<ValidazioneIstanzaResult> ValidaDomandaAsync(int idDomanda)
        {
            var risultatiValidazione = await this._validatorService.ValidaESalvaDomandaAsync(idDomanda);
            if (!risultatiValidazione.ValidazioneRiuscita)
            {
                return ValidazioneIstanzaResult.Failure(risultatiValidazione.Errori);
            }

            return ValidazioneIstanzaResult.Success();
        }
    }
}
