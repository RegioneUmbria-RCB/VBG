// -----------------------------------------------------------------------
// <copyright file="WorkflowInvioDomanda.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------


using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class WorkflowInvioDomanda
    {
        private static class Constants
        {
            public const string IdErroreInvioFallito = "-1";
            public const string IdErroreImportazioneFallita = "-2";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(WorkflowInvioDomanda));
        private readonly IInvioDomandaStrategy _invioIstanzaStrategy;
        private readonly CertificatoDiInvioService _certificatoDiInvioService;

        public WorkflowInvioDomanda(IInvioDomandaStrategy invioIstanzaStrategy, CertificatoDiInvioService certificatoDiInvioService)
        {
            this._invioIstanzaStrategy = invioIstanzaStrategy;
            this._certificatoDiInvioService = certificatoDiInvioService;
        }


        public IInvioIstanzaResult Processa(DomandaOnline domanda, string pecDestinatario)
        {
            var result = this.InviaDomanda(domanda, pecDestinatario);

            if (result.Esito == TipoEsitoInvio.InvioRiuscito)
            {
                var idIstanzaCreata = result.CodiceIstanza;

                this._certificatoDiInvioService.GeneraCertificatoDiInvio(idIstanzaCreata);
            }

            return result;
        }


        private IInvioIstanzaResult InviaDomanda(DomandaOnline domanda, string pecDestinatario)
        {
            this._log.Debug("Invio della domanda...");

            var result = this._invioIstanzaStrategy.Send(domanda, pecDestinatario);

            this._log.DebugFormat("Domanda inviata, Esito: {0}, CodiceIstanza: {1}, NumeroIstanza: {2}", result.Esito, result.CodiceIstanza, result.NumeroIstanza);

            return result;
        }
    }
}
