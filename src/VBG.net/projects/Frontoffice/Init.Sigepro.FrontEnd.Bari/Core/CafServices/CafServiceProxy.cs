// -----------------------------------------------------------------------
// <copyright file="TaresServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using log4net;
using System;
using System.ServiceModel;
using Init.Sigepro.FrontEnd.Bari.BariTaresSigeproServiceReference;
namespace Init.Sigepro.FrontEnd.Bari.Core.CafServices
{
	public class CafServiceProxy : ConfigServiceBase, ICafServiceProxy
	{
		ICafServiceUrl _ITaresServiceUrl;
		IToken _token;
		ILog _log = LogManager.GetLogger(typeof(CafServiceProxy));

		public CafServiceProxy(ICafServiceUrl taresServiceUrl, IConfigServiceUrl configServiceUrl, IToken token, ISoftware software)
			: base(configServiceUrl, token, software)
		{
			this._ITaresServiceUrl = taresServiceUrl;
			this._token = token;
		}

		public bool UtenteAppartieneACaf(string codiceFiscaleOperatore)
		{
			using (var ws = CreaClientWsTares())
			{
				try
				{
					return ws.OperatoreAppartieneACaf(base.GetToken(), codiceFiscaleOperatore);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invocazione a OperatoreAppartieneACaf con il valore {0}: {1}", codiceFiscaleOperatore, ex.ToString());

					ws.Abort();

					throw;
				}
			}
		}

		public string GetCodiceFiscaleCafDaCodiceFiscaleOperatore(string codiceFiscaleOperatore)
		{
			using (var ws = CreaClientWsTares())
			{
				try
				{
					return ws.GetCodiceFiscaleCafDaCodiceFiscaleOperatore(base.GetToken(), codiceFiscaleOperatore);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invocazione a GetCodiceFiscaleCafDaCodiceFiscaleOperatore con il valore {0}: {1}", codiceFiscaleOperatore, ex.ToString());

					ws.Abort();

					throw;
				}
			}
		}

		public RiferimentiCaf GetRiferimentiCafDaCodiceFiscaleoperatore(string codiceFiscaleOperatore)
		{
			using (var ws = CreaClientWsTares())
			{
				try
				{
					return ws.GetRiferimentiCafDaCodiceFiscaleoperatore(base.GetToken(), codiceFiscaleOperatore);
				}
				catch (Exception ex)
				{
					_log.ErrorFormat("Errore durante l'invocazione a GetRiferimentiCafDaCodiceFiscaleoperatore con il valore {0}: {1}", codiceFiscaleOperatore, ex.ToString());

					ws.Abort();

					throw;
				}
			}
		}

		private TaresServiceSoapClient CreaClientWsTares()
		{
			var binding = new BasicHttpBinding("defaultServiceBinding");
			var endpoint = new EndpointAddress(this._ITaresServiceUrl.ServiceUrl);

			return new TaresServiceSoapClient(binding, endpoint);
		}
	}
}
