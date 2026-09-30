using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using VBG.Shared.Infrastructure.Caching;
using Init.Utils;
using Init.Utils.Extensions;
using System;
using System.Reflection;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.WebServices
{
    internal class WsConfigurazioneVbgRepository : IConfigurazioneVbgRepository
    {
        private const string SESSION_KEY_FMT_STRING = "configurazione_comune_{0}_{1}";
        private readonly ConfigurazioneAreaRiservataServiceCreator _serviceCreator;
        private readonly IAliasResolver _aliasResolver;
        private readonly IApplicationCache _webCache;

        public WsConfigurazioneVbgRepository(ConfigurazioneAreaRiservataServiceCreator serviceCreator, IAliasResolver aliasResolver, IApplicationCache webCache)
        {
            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
            this._webCache = webCache;
        }



        public Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione LeggiConfigurazioneComune(string software)
        {
            using (CodeProfiler.Track(MethodBase.GetCurrentMethod()))
            {
                var sessionKey = String.Format(SESSION_KEY_FMT_STRING, this._aliasResolver.AliasComune, software);

                return this._webCache.GetOrAdd(sessionKey, () =>
                {
                    using (var ws = this._serviceCreator.CreateClient())
                    {
                        var cfgComnue = ws.Service.LeggiConfigurazioneComune(ws.Token, software);

                        return cfgComnue.MapUsingJson<Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService.Configurazione>();
                    }
                });
            }
        }
    }
}
