using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class AreaRiservataWsConfigBuilder
    {
        private readonly IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;
        private readonly IAliasSoftwareResolver _aliasResolver;

        protected AreaRiservataWsConfigBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
        {
            if (aliasResolver == null)
                throw new ArgumentNullException("aliasResolver");

            if (configurazioneAreaRiservataRepository == null)
                throw new ArgumentNullException("configurazioneAreaRiservataRepository");


            this._aliasResolver = aliasResolver;
            this._configurazioneAreaRiservataRepository = configurazioneAreaRiservataRepository;
        }

        protected ConfigurazioneAreaRiservataDto GetConfig()
        {
            return this._configurazioneAreaRiservataRepository.DatiConfigurazione(this._aliasResolver.AliasComune, this._aliasResolver.Software);
        }
    }
}
