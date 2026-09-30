using Init.SIGePro.Manager.DTO.Configurazione;
using log4net;
using VBG.Shared.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConfigurazioneContenuti
{
    internal class WsConfigurazioneContenutiRepository : IConfigurazioneContenutiRepository
    {
        private const string SESSION_KEY = "CONFIGURAZIONE_CONTENUTI_SESSION_KEY_";
        private readonly ConfigurazioneContenutiServiceCreator _serviceCreator;
        private readonly ISessionCache _sessionCache;
        private readonly ILog _log = LogManager.GetLogger(typeof(WsConfigurazioneContenutiRepository));

        public WsConfigurazioneContenutiRepository(ConfigurazioneContenutiServiceCreator serviceCreator, ISessionCache sessionCache)
        {
            this._serviceCreator = serviceCreator;
            this._sessionCache = sessionCache;
        }


        public ConfigurazioneContenutiDto GetConfigurazione(string alias, string software)
        {
            var key = SESSION_KEY + alias + "_" + software;

            return this._sessionCache.GetOrAdd(key, () =>
            {
                return this._serviceCreator.Call(ws =>
                {
                    this._log.Debug("Dati di configurazione del FrontEnd non presenti in cache. Lettura della configurazione da web service");

                    return ws.Service.GetConfigurazioneContenutiFrontoffice(ws.Token, software);
                });
            });
        }
    }
}
