using System;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Utils;
using AutoMapper;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	internal class WsConfigurazioneVbgRepository : WsAreaRiservataRepositoryBase, IConfigurazioneVbgRepository
	{
		

		const string SESSION_KEY_FMT_STRING = "configurazione_comune_{0}_{1}";

        IAliasResolver _aliasResolver;


        public WsConfigurazioneVbgRepository(AreaRiservataServiceCreator sc, IAliasResolver aliasResolver)
			: base(sc)
		{
            this._aliasResolver = aliasResolver;
		}



		public Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione LeggiConfigurazioneComune( string software)
		{
			using (var cp = new CodeProfiler("WsConfigurazioneVbgRepository.LeggiConfigurazioneComune"))
			{
				var sessionKey = String.Format(SESSION_KEY_FMT_STRING, this._aliasResolver.AliasComune, software);

                if (!CacheHelper.KeyExists(sessionKey))
				{
                    using (var ws = _serviceCreator.CreateClient())
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
