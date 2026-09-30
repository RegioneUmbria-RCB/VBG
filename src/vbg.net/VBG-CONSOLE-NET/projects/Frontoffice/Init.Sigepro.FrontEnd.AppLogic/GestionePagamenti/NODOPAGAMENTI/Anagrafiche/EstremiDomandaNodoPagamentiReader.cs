using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Anagrafiche
{
    public class EstremiDomandaNodoPagamentiReader : IEstremiDomandaNodoPagamentiReader
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IConfigurazioneNodoPagamentiRepository _repository;
        private readonly IComuniService _comuniService;
        private readonly ILog _log = LogManager.GetLogger(typeof(EstremiDomandaNodoPagamentiReader));

        public EstremiDomandaNodoPagamentiReader(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IConfigurazioneNodoPagamentiRepository repository, IComuniService comuniService)
        {
            _salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            _repository = repository;
            _comuniService = comuniService;
        }


        public EstremiDomandaNodoPagamenti GetEstremiDomandaDaDomanda(DomandaOnline domanda, int lastWorkflowStep)
        {
            var configurazione = this._repository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);

            if (this._log.IsDebugEnabled)
            {
                this._log.Debug($"Inizio risoluzione dell'intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione}, cerco il tipo soggetto {configurazione.SoggettoPendenza}");
            }

            var intestatarioPendenza = GetIntestatarioPendenza(domanda.ReadInterface, configurazione.SoggettoPendenza);

            if (this._log.IsDebugEnabled)
            {
                this._log.Debug($"Soggetto trovato come intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione}: {intestatarioPendenza}");
            }

            var email = intestatarioPendenza.Contatti?.Email;

            if (String.IsNullOrEmpty(email))
            {
                this._log.Debug($"Il soggetto intestatario della pendenza per la domanda {domanda.DataKey.IdPresentazione} non ha un indirizzo email definito, verrà utilizzato il domicilio elettronico");

                email = domanda.ReadInterface.AltriDati.DomicilioElettronico;
            }

            if (String.IsNullOrEmpty(email))
            {
                this._log.Error(
                    $"Non è stato possibile ricavare un indirizzo email per l'intestatario della pendenza " +
                    $"(cercato tra email nei contatti e nel domicilio elettronico): " +
                    $"idDomanda={domanda.DataKey.IdPresentazione}, intestatarioPendenza={intestatarioPendenza}");

                throw new EmailIntestatarioPendenzaNonTrovataException(
                    "Impossibile inizializzare il pagamento perchè non è possibile ricavare l'indirizzo email dell'intestatario del pagamento. " +
                    "Tornare allo step di inserimento anagrafiche e inserire un indirizzo email valido.");
            }

            return new EstremiDomandaNodoPagamenti(domanda.DataKey.IdPresentazione, lastWorkflowStep, intestatarioPendenza, this._comuniService, email);
        }

        public EstremiDomandaNodoPagamenti GetEstremiDomandaDaIdDomanda(int idDomanda, int lastWorkflowStep)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return GetEstremiDomandaDaDomanda(domanda, lastWorkflowStep);
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
