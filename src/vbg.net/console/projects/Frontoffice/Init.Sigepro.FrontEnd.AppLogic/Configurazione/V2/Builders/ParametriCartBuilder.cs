// -----------------------------------------------------------------------
// <copyright file="ParametriCartBuilder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	internal class ParametriCartBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriCart>
	{
		public ParametriCartBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{

		}

		#region IConfigurazioneBuilder<ParametriCart> Members

		public ParametriCart Build()
		{
			var cfg = GetConfig();

			var urlApplicazioneFacct = cfg.UrlApplicazioneFacct;
            var urlAccettatore = cfg.ConfigurazioneCart == null ? String.Empty : cfg.ConfigurazioneCart.UrlAccettatore;

			return new ParametriCart(urlApplicazioneFacct, urlAccettatore);
		}

		#endregion
	}
}
