using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriAreaRiservataCoreBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAreaRiservataCore>
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriAreaRiservataCoreBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IAppConfigurationReader appConfigurationReader)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriAreaRiservataCore Build()
        {
            var config = this.GetConfig();
            var usaAreaRiservataCore = config.AreaRiservataCore?.UsaAreaRiservataCore ?? false;
            var baseUrlCore = String.IsNullOrEmpty(config.AreaRiservataCore?.BaseUrlCore) ? null : config.AreaRiservataCore!.BaseUrlCore;
            var baseUrlFramework = String.IsNullOrEmpty(config.AreaRiservataCore?.BaseUrlFramework) ? null : config.AreaRiservataCore!.BaseUrlFramework;

            var parametri = this._appConfigurationReader.GetParametriPerComune();
            if (parametri.ForzaUsoArCore)
            {
                usaAreaRiservataCore = true;
            }

            var usaPresentazioneDomandaCore = parametri.UsaPresentazioneDomandeCore;

            return new ParametriAreaRiservataCore(usaAreaRiservataCore, baseUrlCore, baseUrlFramework, usaPresentazioneDomandaCore);
        }
    }
}
