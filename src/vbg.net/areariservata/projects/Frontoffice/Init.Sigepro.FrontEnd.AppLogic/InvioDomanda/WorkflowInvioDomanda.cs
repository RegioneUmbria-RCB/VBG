// -----------------------------------------------------------------------
// <copyright file="WorkflowInvioDomanda.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{

    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using log4net;

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


		ILog _log = LogManager.GetLogger(typeof(WorkflowInvioDomanda));
		IInvioDomandaStrategy _invioIstanzaStrategy;
		IMessaggiNotificaInvioService _messaggiNotificaInvioService;
		CertificatoDiInvioService _certificatoDiInvioService;

		public WorkflowInvioDomanda(IInvioDomandaStrategy invioIstanzaStrategy, IMessaggiNotificaInvioService messaggiNotificaInvioService, CertificatoDiInvioService certificatoDiInvioService)
		{
			this._invioIstanzaStrategy = invioIstanzaStrategy;
			this._messaggiNotificaInvioService = messaggiNotificaInvioService;
			this._certificatoDiInvioService = certificatoDiInvioService;
		}


		public InvioIstanzaResult Processa(DomandaOnline domanda, string pecDestinatario)
		{
			var result = InviaDomanda(domanda, pecDestinatario);

			if (result.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito)
			{
				var idIstanzaCreata = result.CodiceIstanza;

				this._messaggiNotificaInvioService.Invia(domanda.DataKey.IdPresentazione, idIstanzaCreata);
				this._certificatoDiInvioService.GeneraCertificatoDiInvio(domanda, idIstanzaCreata);
			}

			return result;
		}


		private InvioIstanzaResult InviaDomanda(DomandaOnline domanda, string pecDestinatario)
		{
			_log.Debug("Invio della domanda...");

			var result = _invioIstanzaStrategy.Send(domanda, pecDestinatario);

			_log.DebugFormat("Domanda inviata, Esito: {0}, CodiceIstanza: {1}, NumeroIstanza: {2}", result.Esito, result.CodiceIstanza, result.NumeroIstanza);

			return result;
		}
	}
}
