
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Model.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
	public class EsitoInvio
	{
		public class EsitoinvioSettings
		{
			[Inject]
			public IConfigurazione<ParametriInvio> Configurazione { get; set; }
			[Inject]
			public MessaggioInvioFallito MessaggioInvioFallito { get; set; }
		}

		public bool DomandaInviataConSuccesso { get; private set; }
		public string MessaggioErrore { get; private set; }
		public string CodiceIstanza { get; private set; }
		public string NumeroIstanza { get; private set; }
		public string CodiceDomandaMittente { get; private set; }

		public static EsitoInvio FromInvioIstanzaResult(InvioIstanzaResult result , DomandaOnline domanda)
		{
			var settings = new EsitoinvioSettings();

			FoKernelContainer.Inject( settings );

			if (result.EsitoInvio)
			{
				// Se result.CodiceIstanza == key.ToSerializationCode() probabilmente la pratica è stata inviata via PEC.
				// In questo caso simulo un invio fallito e riporto nel messaggio di errore il testo del 
				if (result.CodiceIstanza == domanda.GetPresentazioneIstanzaDataKey().ToSerializationCode())
				{
					return new EsitoInvio
					{
						DomandaInviataConSuccesso = false,
						MessaggioErrore = settings.Configurazione.Parametri.MessaggioInvioPec,
						CodiceIstanza = result.CodiceIstanza,
						NumeroIstanza = result.NumeroIstanza,
						CodiceDomandaMittente = domanda.GetPresentazioneIstanzaDataKey().ToSerializationCode()
					};
				}

				// TODO: Spostare l'aggiunta del certificato di invio in una pagina successiva all'invio
				return new EsitoInvio
				{
					DomandaInviataConSuccesso = true,
					CodiceIstanza = result.CodiceIstanza,
					NumeroIstanza = result.NumeroIstanza,
					CodiceDomandaMittente = domanda.GetPresentazioneIstanzaDataKey().ToSerializationCode()
				};
			}

			var msgInvioFallito = settings.MessaggioInvioFallito.Get(domanda.GetPresentazioneIstanzaDataKey().ToSerializationCode());

			return new EsitoInvio
			{
				DomandaInviataConSuccesso = false,
				MessaggioErrore = msgInvioFallito,
				CodiceDomandaMittente = domanda.GetPresentazioneIstanzaDataKey().ToSerializationCode()
			};
		}
	}
}
