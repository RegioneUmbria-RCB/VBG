using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Microsoft.Extensions.Logging;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Anagrafiche
{
    public class EstremiDomandaNodoPagamentiReader : IEstremiDomandaNodoPagamentiReader
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IConfigurazioneNodoPagamentiRepository _repository;
        private readonly IComuniService _comuniService;
        private readonly ILogger<EstremiDomandaNodoPagamentiReader> _log;

        public EstremiDomandaNodoPagamentiReader(ILoggerFactory loggerFactory, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IConfigurazioneNodoPagamentiRepository repository, IComuniService comuniService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._repository = repository;
            this._comuniService = comuniService;
            this._log = loggerFactory.CreateLogger<EstremiDomandaNodoPagamentiReader>();
        }


        public EstremiDomandaNodoPagamenti GetEstremiDomandaDaDomanda(DomandaOnline domanda, int lastWorkflowStep)
        {
            try
            {
                var configurazione = this._repository.GetConfigurazione(domanda.ReadInterface.AltriDati.CodiceComune);

                if (this._log.IsEnabled(LogLevel.Debug))
                {
                    this._log.LogDebug($"Inizio risoluzione dell'intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione}, cerco il tipo soggetto {configurazione.SoggettoPendenza}");
                }

                var intestatarioPendenza = GetIntestatarioPendenza(domanda.ReadInterface, configurazione.SoggettoPendenza);

                if (this._log.IsEnabled(LogLevel.Debug))
                {
                    this._log.LogDebug($"Soggetto trovato come intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione}: {intestatarioPendenza}");
                }

                var email = intestatarioPendenza.Contatti?.Email;

                if (String.IsNullOrEmpty(email))
                {
                    this._log.LogDebug($"Il soggetto intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione} non ha un indirizzo email definito, verrà utilizzato il domicilio elettronico");

                    email = domanda.ReadInterface.AltriDati.DomicilioElettronico;
                }

                if (String.IsNullOrEmpty(email))
                {
                    this._log.LogError(
                        $"Non è stato possibile ricavare un indirizzo email per l'intestatario della pendenza " +
                        $"(cercato tra email nei contatti e nel domicilio elettronico): " +
                        $"idDomanda={domanda.DataKey.IdPresentazione}, intestatarioPendenza={intestatarioPendenza}");

                    throw new EmailIntestatarioPendenzaNonTrovataException(
                        "Impossibile inizializzare il pagamento perchè non è possibile ricavare l'indirizzo email dell'intestatario del pagamento. " +
                        "Tornare allo step di inserimento anagrafiche e inserire un indirizzo email valido.");
                }

                return new EstremiDomandaNodoPagamenti(domanda.DataKey.IdPresentazione, lastWorkflowStep, intestatarioPendenza, this._comuniService, email);
            }
            catch (Exception ex)
            {
                this._log.LogError("Errore in {@metodo}: {@ex}", nameof(GetEstremiDomandaDaDomanda), ex);
                throw;
            }
        }

        public EstremiDomandaNodoPagamenti GetEstremiDomandaDaIdDomanda(int idDomanda, int lastWorkflowStep)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return this.GetEstremiDomandaDaDomanda(domanda, lastWorkflowStep);
        }

        private static AnagraficaDomanda GetIntestatarioPendenza(IDomandaOnlineReadInterface readInterface, SoggettoPendenzaEnum soggettoPendenza)
        {
            if (soggettoPendenza == SoggettoPendenzaEnum.Azienda)
            {
                var azienda = readInterface.Anagrafiche.GetAzienda();

                if (azienda != null)
                {
                    return azienda;
                }
            }

            return readInterface.Anagrafiche.GetRichiedente();
        }
    }
}
