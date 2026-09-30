namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	using System;
	using System.Configuration;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

    internal class ParametriFirmaDigitaleBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriFirmaDigitale>
	{
		private static class Constants
		{
			public const string ConfigKeyName = "FirmaDigitale.UrlJspFirmaDigitale";
		}

        public ParametriFirmaDigitaleBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

		public ParametriFirmaDigitale Build()
		{
			var cfgKey = ConfigurationManager.AppSettings[Constants.ConfigKeyName];
            var config = GetConfig();

			if (string.IsNullOrEmpty(cfgKey))
			{
				throw new Exception("Nel web.config non è stato configurato in parametro " + Constants.ConfigKeyName);
			}

            return new ParametriFirmaDigitale(cfgKey, config.IdSchedaEstremiDocumento);
		}
	}
}
