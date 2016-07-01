// -----------------------------------------------------------------------
// <copyright file="MessaggioInvioFallito.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using CuttingEdge.Conditions;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MessaggioInvioFallito
	{
		IConfigurazione<ParametriInvio> _configurazione;
		IConfigurazioneVbgRepository _configurazioneVbgRepository;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		public MessaggioInvioFallito(IConfigurazione<ParametriInvio> configurazione, IConfigurazioneVbgRepository configurazioneVbgRepository, IAliasSoftwareResolver aliasSoftwareResolver)
		{
			Condition.Requires(configurazione, "configurazione").IsNotNull();
			Condition.Requires(configurazioneVbgRepository, "configurazioneVbgRepository").IsNotNull();
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

			_configurazione = configurazione;
			_configurazioneVbgRepository = configurazioneVbgRepository;
			_aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public string Get(string idDomanda)
		{
			var fmtStr = _configurazione.Parametri.MessaggioInvioFallito;

			var boConfig = _configurazioneVbgRepository.LeggiConfigurazioneComune(_aliasSoftwareResolver.AliasComune, _aliasSoftwareResolver.Software);

			var msgInvio = string.Format(fmtStr, boConfig.TELEFONO, boConfig.ORARIO);

			return msgInvio + "<br /><br /><b>Numero pratica:</b><br />" + idDomanda;
		}
	}
}
