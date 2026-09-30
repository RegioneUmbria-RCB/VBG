using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal interface ICodiceAccreditamentoHelper
	{
		string GetCodiceAccreditamento();
	}


	internal class CodiceAccreditamentoHelper : ICodiceAccreditamentoHelper
	{
		IConfigurazioneVbgRepository _configurazioneVbgRepository;
		IAliasSoftwareResolver _aliasSoftwareResolver;

		internal CodiceAccreditamentoHelper(IConfigurazioneVbgRepository configurazioneVbgRepository, IAliasSoftwareResolver aliasSoftwareResolver)
		{
			this._configurazioneVbgRepository = configurazioneVbgRepository;
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public string GetCodiceAccreditamento()
		{
			var cfg = _configurazioneVbgRepository.LeggiConfigurazioneComune(this._aliasSoftwareResolver.Software);

			return cfg.CodiceAccreditamento;
		}
	}
}
