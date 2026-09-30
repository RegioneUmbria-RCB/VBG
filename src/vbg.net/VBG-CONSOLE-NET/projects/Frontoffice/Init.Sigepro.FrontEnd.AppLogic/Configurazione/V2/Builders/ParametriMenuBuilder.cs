using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using log4net;
using System;


namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriMenuBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriMenuV2>
	{
		ILog _log = LogManager.GetLogger(typeof(ParametriMenuBuilder));
		IAliasSoftwareResolver _aliasResolver;

		public ParametriMenuBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
			: base(aliasResolver, configurazioneAreaRiservataRepository)
		{
			this._aliasResolver = aliasResolver;
		}


		#region IBuilder<ParametriMenu> Members

		public ParametriMenu Build()
		{
			var cfg = GetConfig();

            throw new NotImplementedException();
		}

		#endregion

        ParametriMenuV2 IConfigurazioneBuilder<ParametriMenuV2>.Build()
        {
            var cfg = GetConfig();

            return new ParametriMenuV2(cfg.CodiceOggettoMenuXml);
        }
    }
}
