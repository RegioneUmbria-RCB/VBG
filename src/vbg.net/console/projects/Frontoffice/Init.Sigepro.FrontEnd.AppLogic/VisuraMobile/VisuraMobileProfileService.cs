// -----------------------------------------------------------------------
// <copyright file="VisuraMobileProfileService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.VisuraMobile
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using System.ServiceModel;
using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class VisuraMobileProfileService : IVisuraMobileProfileService
	{
		IConfigurazione<ParametriVisura> _config;
		ILog _log = LogManager.GetLogger(typeof(VisuraMobileProfileService));

		public VisuraMobileProfileService(IConfigurazione<ParametriVisura> config)
		{
			this._config = config;
		}

		public bool RegistraProfilo(string uid, string nome, string cognome, string codiceFiscale)
		{
			var endPoint = new EndpointAddress(this._config.Parametri.VisuraMobile.UrlServizi);

			var binding = new BasicHttpBinding("areaRiservataServiceBinding");
			using (var ws = new VisuraMobileServices.StcMobileWSClient(binding, endPoint))
			{
				try
				{
					var alias = this._config.Parametri.VisuraMobile.AliasSportello;
					codiceFiscale = codiceFiscale.ToUpperInvariant();

					_log.DebugFormat("Chiamata a StcMobileWSClient: uid={0}, nome={1}, cognome={2}, codiceFiscale={3},aliasSportello={4}", uid, nome, cognome, codiceFiscale, alias);

					var outMsg = String.Empty;
					var result = ws.registraProfilo(uid, nome, cognome, codiceFiscale, alias, out outMsg);  

					_log.DebugFormat("StcMobileWSClient invocato con successo: result={0}, outMsg={1}", result, outMsg);

					if (result != 0)
					{
						_log.ErrorFormat("Chiamata a StcMobileWSClient fallita: uid={0}, nome={1}, cognome={2}, codiceFiscale={3},aliasSportello={4}, status={5}, errore={6}", uid, nome, cognome, codiceFiscale, alias, result, outMsg);

						return false;
					}

					return true;
				}
				catch (Exception ex)
				{
					this._log.ErrorFormat("Errore durante l'invocazione di StcMobileWSClient.registraProfilo: {0}", ex.ToString());

					ws.Abort();

					return false;
				}
			}
		}
	}
}
