using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
	internal class AreaRiservataWsConfigBuilder
	{
		IConfigurazioneAreaRiservataRepository _configurazioneAreaRiservataRepository;
		IAliasSoftwareResolver				   _aliasResolver;

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
