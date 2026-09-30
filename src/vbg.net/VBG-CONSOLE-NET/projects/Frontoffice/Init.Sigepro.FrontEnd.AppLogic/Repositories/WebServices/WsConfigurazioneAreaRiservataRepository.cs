namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
	using System;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
	using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
	using Init.Sigepro.FrontEnd.Infrastructure.Caching;
	using log4net;

	internal class WsConfigurazioneAreaRiservataRepository : WsAreaRiservataRepositoryBase, IConfigurazioneAreaRiservataRepository
	{
        private static class Constants
        {
            public const string CacheKey = "WsConfigurazioneAreaRiservataRepository.Cache";
        }

		ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneAreaRiservataRepository));

        ApplicationLevelCacheHelper _cache;

		public WsConfigurazioneAreaRiservataRepository(AreaRiservataServiceCreator sc, ApplicationLevelCacheHelper cache)
			: base(sc)
		{
            this._cache = cache;
		}

        public ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software)
        {
            var cacheKey = $"{Constants.CacheKey}.{idComune}.{software}";

            return this._cache.GetOrAdd(cacheKey, () =>
            {
                _log.Debug("Dati di configurazione del FrontEnd non presenti in cache. Lettura della configurazione da web service");

                using (var ws = _serviceCreator.CreateClient(idComune))
                {
                    try
                    {
                        var cfg = ws.Service.LeggiConfigurazioneFrontoffice(ws.Token, software);

                        return cfg;
                    }
                    catch (Exception)
                    {
                        ws.Service.Abort();
                        throw;
                    }
                }
            });

			
		}
	}
}
