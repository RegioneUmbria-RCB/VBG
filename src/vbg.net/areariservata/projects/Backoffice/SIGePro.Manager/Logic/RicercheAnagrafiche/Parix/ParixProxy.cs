// -----------------------------------------------------------------------
// <copyright file="ParixProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Manager.ParixGateService;
	using System.ServiceModel;
	using System.ServiceModel.Channels;
	using System.Net;
	using log4net;


	internal class ParixProxy
	{
		ConfigurazioneParix _verticalizzazione;
		ILog _log = LogManager.GetLogger(typeof(ParixProxy));

		public ParixProxy(ConfigurazioneParix verticalizzazione)
		{
			this._verticalizzazione = verticalizzazione;
		}

		public string DettaglioRidottoImpresa(string CCIAA, string NREA)
		{
			var config = this._verticalizzazione.Get;

			using (var ws = CreaWebService())
			{
				ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;

				using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
				{
					AggiungiCredenzialiCartAContextScope(config.BasicAuthUser, config.BasicAuthPassword);

					_log.DebugFormat("DettagliRidottoImpresa, prarametri: CCIAA={0}, NREA={1}, config.Switchcontrol={2}, config.User={3}, config.Password={4}", CCIAA, NREA, config.Switchcontrol, config.User, config.Password);

					string result = ws.DettaglioRidottoImpresa(CCIAA, NREA, config.Switchcontrol, config.User, config.Password);

					_log.Debug("result: " + result);

					return result;
				}
			}
		}

		public string RicercaImpreseNonCessatePerCodiceFiscale(string partitaIva)
		{
			var config = this._verticalizzazione.Get;

			using (var ws = CreaWebService())
			{
				ServicePointManager.ServerCertificateValidationCallback += (sender, cert, chain, error) => true;

				using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
				{
					AggiungiCredenzialiCartAContextScope(config.BasicAuthUser, config.BasicAuthPassword);

					_log.DebugFormat("RicercaImpreseNonCessatePerCodiceFiscale: partitaIva={0}, config.Switchcontrol={1}, config.User={2}, config.Password={3}", partitaIva, config.Switchcontrol, config.User, config.Password);

					string result = ws.RicercaImpreseNonCessatePerCodiceFiscale(partitaIva, config.Switchcontrol, config.User, config.Password);

					_log.Debug("result: " + result);

					return result;
				}
			}
		}

		private void AggiungiCredenzialiCartAContextScope(string username, string password)
		{
			if (!String.IsNullOrEmpty(username))
			{
				_log.Debug("Il parametro BasicAuthUser non è vuoto, verrà utilizzata l'autenticazione basic per effettuare la chiamata a parix");

				var credentials = GetCartCredentials(username, password);
				var request = new HttpRequestMessageProperty();

				request.Headers[System.Net.HttpRequestHeader.Authorization] = "Basic " + credentials;

				OperationContext.Current.OutgoingMessageProperties.Add(HttpRequestMessageProperty.Name, request);
			}
		}


		private CRSimpleWSImplClient CreaWebService()
		{
			var config = this._verticalizzazione.Get;

			var endPointAddress = new EndpointAddress(config.Url);
			var binding = new BasicHttpBinding("parixHttpBinding");

			binding.MaxReceivedMessageSize = 2147483647;
			binding.ReaderQuotas.MaxStringContentLength = 2048000;

			var uri = new Uri(config.Url);

			binding.Security.Mode = (uri.Scheme.ToUpperInvariant() == "HTTPS") ? BasicHttpSecurityMode.Transport : BasicHttpSecurityMode.None;

			if (!String.IsNullOrEmpty(config.ProxyAddress))
			{
				binding.UseDefaultWebProxy = false;
				binding.ProxyAddress = new Uri(config.ProxyAddress);
			}

			var svc = new CRSimpleWSImplClient(binding, endPointAddress);

			return svc;
		}

		private string GetCartCredentials(string username, string password)
		{
			var credentials = username + ":" + password;

			return Convert.ToBase64String(Encoding.UTF8.GetBytes(credentials));
		}


	}
}
