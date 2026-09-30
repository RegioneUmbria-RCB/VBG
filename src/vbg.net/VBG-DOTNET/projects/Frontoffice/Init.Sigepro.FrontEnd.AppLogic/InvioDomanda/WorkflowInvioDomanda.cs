// -----------------------------------------------------------------------
// <copyright file="WorkflowInvioDomanda.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Microsoft.Extensions.Logging;
    using System;
    using System.Threading.Tasks;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class WorkflowInvioDomanda
    {
        private readonly ILogger<WorkflowInvioDomanda> _log;
        private readonly IInvioDomandaStrategy _invioIstanzaStrategy;
        // private readonly IMessaggiNotificaInvioService _messaggiNotificaInvioService;
        private readonly CertificatoDiInvioService _certificatoDiInvioService;
        private readonly IEventiDomandeInBozzaService _eventiDomandeInBozzaService;
        private readonly IOnceOnlyAnagraficheService _onceOnlyService;

        public WorkflowInvioDomanda(ILoggerFactory loggerFactory, IInvioDomandaStrategy invioIstanzaStrategy,/* IMessaggiNotificaInvioService messaggiNotificaInvioService,*/
            CertificatoDiInvioService certificatoDiInvioService,
            IEventiDomandeInBozzaService eventiDomandeInBozzaService,
            IOnceOnlyAnagraficheService onceOnlyService)
        {
            this._invioIstanzaStrategy = invioIstanzaStrategy;
            // this._messaggiNotificaInvioService = messaggiNotificaInvioService;
            this._certificatoDiInvioService = certificatoDiInvioService;
            this._eventiDomandeInBozzaService = eventiDomandeInBozzaService;
            this._onceOnlyService = onceOnlyService;
            this._log = loggerFactory.CreateLogger<WorkflowInvioDomanda>();
        }

        public async Task<InvioIstanzaResult> ProcessaAsync(DomandaOnline domanda, ParametriInvioDomanda parametriInvio)
        {
            var result = await this.InviaDomandaAsync(domanda, parametriInvio);

            var risultato = await this.ProcessaPostInvioAsync(domanda, result, parametriInvio);

            await this.SalvaSoggettiOnceOnlyAsync(domanda);

            return risultato;
        }
#if NET48
        public InvioIstanzaResult Processa(DomandaOnline domanda, string pecDestinatario)
        {
            var result = this.InviaDomanda(domanda, pecDestinatario);

            return this.ProcessaPostInvio(domanda, result);
        }
#endif
        private async Task SalvaSoggettiOnceOnlyAsync(DomandaOnline domanda)
        {
            if (!this._onceOnlyService.IsOnceOnlyAttivo)
            {
                return;
            }

            try
            {
                await this._onceOnlyService.SalvaSoggettiDomandaAsync(domanda.ReadInterface);
            }
            catch (Exception ex)
            {
                this._log.LogError("Impossibile salvare i soggetti della domanda {@idDomanda}: {@ex}", domanda.ReadInterface.AltriDati.IdentificativoDomanda, ex);
            }
        }

        private async Task<InvioIstanzaResult> ProcessaPostInvioAsync(DomandaOnline domanda, InvioIstanzaResult result, ParametriInvioDomanda parametriInvio)
        {
            if (!String.IsNullOrEmpty(result.CodiceIstanza))
            {
                this._eventiDomandeInBozzaService.DomandaInBozzaPresentata(domanda.ReadInterface);
            }

            if (result.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito && parametriInvio.GeneraEAllegaCertificatoDiInvio)
            {
                var idIstanzaCreata = Convert.ToInt32(result.CodiceIstanza);

                // this._messaggiNotificaInvioService.Invia(domanda.DataKey.IdPresentazione, idIstanzaCreata.ToString());

                // La domanda PNRR non genera il certificato di invio
                //if (this._runtimeEnvironment.EnvironmentType != RuntimeEnvironmentType.DomandaOnLine)
                //{
                await this._certificatoDiInvioService.GeneraCertificatoDiInvioAsync(idIstanzaCreata);
                //}
            }

            return result;
        }


#if NET48
        private InvioIstanzaResult ProcessaPostInvio(DomandaOnline domanda, InvioIstanzaResult result)
        {
            if (!String.IsNullOrEmpty(result.CodiceIstanza))
            {
                this._eventiDomandeInBozzaService.DomandaInBozzaPresentata(domanda.ReadInterface);
            }

            if (result.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito)
            {
                var idIstanzaCreata = Convert.ToInt32(result.CodiceIstanza);

                // this._messaggiNotificaInvioService.Invia(domanda.DataKey.IdPresentazione, idIstanzaCreata.ToString());

                // La domanda PNRR non genera il certificato di invio
                this._certificatoDiInvioService.GeneraCertificatoDiInvio(idIstanzaCreata);
            }

            return result;
        }
#endif


        private async Task<InvioIstanzaResult> InviaDomandaAsync(DomandaOnline domanda, ParametriInvioDomanda parametriInvio)
        {
            this._log.LogDebug("Invio della domanda...");

            var result = await this._invioIstanzaStrategy.SendAsync(domanda, parametriInvio.PecDestinatario, parametriInvio.SportelloDestinatario, parametriInvio.VerificaEsistenzaPraticaNelBackoffice);

            this._log.LogDebug("Domanda inviata, Esito: {@esito}, CodiceIstanza: {@codiceIstanza}, NumeroIstanza: {@numeroIstanza}", result.Esito, result.CodiceIstanza, result.NumeroIstanza);

            return result;
        }

        private InvioIstanzaResult InviaDomanda(DomandaOnline domanda, string pecDestinatario)
        {
            this._log.LogDebug("Invio della domanda...");

            var result = this._invioIstanzaStrategy.Send(domanda, pecDestinatario);

            this._log.LogDebug("Domanda inviata, Esito: {@esito}, CodiceIstanza: {@codiceIstanza}, NumeroIstanza: {@numeroIstanza}", result.Esito, result.CodiceIstanza, result.NumeroIstanza);

            return result;
        }
    }
}
