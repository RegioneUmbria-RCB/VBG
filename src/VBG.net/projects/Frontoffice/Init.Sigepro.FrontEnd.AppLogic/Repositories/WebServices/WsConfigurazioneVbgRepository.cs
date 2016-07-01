using System;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Utils;
using AutoMapper;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsConfigurazioneVbgRepository : WsAreaRiservataRepositoryBase, IConfigurazioneVbgRepository
	{
		

		const string SESSION_KEY_FMT_STRING = "configurazione_comune_{0}_{1}";

		public WsConfigurazioneVbgRepository(AreaRiservataServiceCreator sc)
			: base(sc)
		{

		}



		public Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione LeggiConfigurazioneComune(string idComune, string software)
		{
			using (var cp = new CodeProfiler("WsConfigurazioneVbgRepository.LeggiConfigurazioneComune"))
			{
				var sessionKey = String.Format(SESSION_KEY_FMT_STRING, idComune, software);

				if (!CacheHelper.KeyExists(sessionKey))
				{
					using (var ws = _serviceCreator.CreateClient(idComune))
					{
						var cfgComnue = ws.Service.LeggiConfigurazioneComune(ws.Token, software);

						var cfgConvertita = new Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione();

						//ClassMapper.CopyProperties(cfgComnue, cfgConvertita);
						Mapper.Map(cfgComnue, cfgConvertita);

						return CacheHelper.AddEntry(sessionKey, cfgConvertita);
					}
				}

				return CacheHelper.GetEntry<Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione>(sessionKey);
			}
		}
	}
}
