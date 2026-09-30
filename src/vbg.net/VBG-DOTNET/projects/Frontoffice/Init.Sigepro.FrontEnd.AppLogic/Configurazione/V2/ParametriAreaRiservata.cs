using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriAreaRiservata : IParametriConfigurazione
    {
        public string PaginaIniziale { get; }

        public ParametriAreaRiservata(string urlPaginaIniziale)
        {
            this.PaginaIniziale = urlPaginaIniziale;
        }
    }

    public class ParametriAreaRiservataBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAreaRiservata>
    {
        private readonly IAppConfigurationReader _appConfigurationReader;

        public ParametriAreaRiservataBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IAppConfigurationReader appConfigurationReader) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._appConfigurationReader = appConfigurationReader;
        }

        public ParametriAreaRiservata Build()
        {
            var parametriLocalizzati = this._appConfigurationReader.GetParametriPerComune();
            var parametriWs = this.GetConfig();

            var urlPaginaIniziale = parametriWs.UrlPaginaIniziale;

            if (String.IsNullOrEmpty(urlPaginaIniziale))
                urlPaginaIniziale = parametriLocalizzati.PaginaIniziale;

            return new ParametriAreaRiservata(urlPaginaIniziale);
        }
    }
}
