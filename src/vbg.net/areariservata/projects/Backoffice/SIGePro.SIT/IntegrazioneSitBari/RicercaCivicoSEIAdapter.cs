using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.SitBariReferences.RicercaCivicoService;
using Init.SIGePro.Sit.IntegrazioneSitBari.RicercaCivico;
using Init.SIGePro.Sit.Utils;
using log4net;
using Init.SIGePro.Verticalizzazioni;
using System.ServiceModel;

namespace Init.SIGePro.Sit.IntegrazioneSitBari
{
	public interface IRicercaCivicoSEIAdapter
	{
		responseType EseguiRicerca(requestType richiesta);
	}

	public class RicercaCivicoSEIAdapter : IRicercaCivicoSEIAdapter
	{
		private static class Constants
		{
			public const string DefaultRicercaCivicoServiceUrl = "http://sit.comba.comune.bari.it:80/ServiziToponomastica/services/RicercaCivicoEPPort";
			public const string RicercaCivicoBinding = "defaultHttpBinding";
		}

		ILog _log = LogManager.GetLogger(typeof(RicercaCivicoSEIAdapter));

		string _ricercaCivicoServiceurl = Constants.DefaultRicercaCivicoServiceUrl;

		public RicercaCivicoSEIAdapter(VerticalizzazioneSitBari verticalizzazione)
		{
			if (!String.IsNullOrEmpty(verticalizzazione.WsUrlValidazioneCivico))
				this._ricercaCivicoServiceurl = verticalizzazione.WsUrlValidazioneCivico;
		}


		public responseType EseguiRicerca(requestType richiesta)
		{
			_log.DebugFormat("Inizializzazione del web service di validazione civici con i parametri ricercaCivicoServiceurl={0} e bindingName={1}",
								this._ricercaCivicoServiceurl,
								Constants.RicercaCivicoBinding);

			var binding = new BasicHttpBinding(Constants.RicercaCivicoBinding);
			var address = new EndpointAddress(this._ricercaCivicoServiceurl);

			using (var ws = new RicercaCivicoSEIClient( binding , address))
			{
				try
				{
					var strRichiesta = richiesta.XmlSerializeToString();

					_log.DebugFormat("Invocazione di eseguiRichiesta con il parametro {0}", strRichiesta);

					var response = ws.eseguiRichiesta(richiesta.XmlSerializeToString());

					_log.DebugFormat("Risposta del web service {0}", response);

					return SerializationExtensions.XmlDeserializeFromString<responseType>(response);
				}
				catch (System.Exception ex)
				{
					ws.Abort();

					_log.ErrorFormat("La chiamata a RicercaCivicoSEIClient con i parametri {0} ha restituito l'errore {1}", richiesta.XmlSerializeToString(), ex.ToString());

					throw;
				}
			}
		}
	}
}
