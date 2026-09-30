using Init.SIGePro.Manager.DTO.Configurazione;
using log4net;
using System;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference
{
    public class WsConfigurazioneAreaRiservataRepository : IConfigurazioneAreaRiservataRepository
    {
        private static class Constants
        {
            public const string CacheKey = "WsConfigurazioneAreaRiservataRepository.Cache";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneAreaRiservataRepository));
        private readonly ConfigurazioneAreaRiservataServiceCreator _serviceCreator;
        private readonly IApplicationCache _cache;

        public WsConfigurazioneAreaRiservataRepository(ConfigurazioneAreaRiservataServiceCreator serviceCreator, IApplicationCache cache)
        {
            this._serviceCreator = serviceCreator;
            this._cache = cache;
        }

        public ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software)
        {
            var cacheKey = $"{Constants.CacheKey}.{idComune}.{software}";

            return this._cache.GetOrAdd(cacheKey, () =>
            {
                this._log.Debug("Dati di configurazione del FrontEnd non presenti in cache. Lettura della configurazione da web service");

                return this._serviceCreator.Call(ws =>
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
                });
            });


        }
    }
}
