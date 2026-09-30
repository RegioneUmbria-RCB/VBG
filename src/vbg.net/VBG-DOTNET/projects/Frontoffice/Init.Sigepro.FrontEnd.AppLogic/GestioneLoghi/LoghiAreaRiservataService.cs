using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using VBG.Shared.Infrastructure.Caching;
using System;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi
{
    public class LoghiAreaRiservataService
    {
        private static class Constants
        {
            public const string IdLogoStiliFrontoffice = "logo_suap";
            public const string Base64Empty = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuOWwzfk4AAAANSURBVBhXY/j//z8DAAj8Av6IXwbgAAAAAElFTkSuQmCC";
        }

        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IOggettiService _oggettiService;
        private readonly IRisorseFrontofficeService _risorseService;
        private readonly IConfigurazione<ParametriLoghi> _parametriLoghi;
        private readonly IApplicationCache _applicationCache;

        public LoghiAreaRiservataService(IAliasSoftwareResolver aliasSoftwareResolver, IOggettiService oggettiService, IConfigurazione<ParametriLoghi> parametriLoghi, IApplicationCache applicationCache, IRisorseFrontofficeService risorseService)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._oggettiService = oggettiService;
            this._parametriLoghi = parametriLoghi;
            this._applicationCache = applicationCache;
            this._risorseService = risorseService;
        }

        public BinaryFile GetLogoRegione()
        {
            var cacheKey = $"LogoRegione.{this._aliasSoftwareResolver.AliasComune}.{this._aliasSoftwareResolver.Software}";

            return this._applicationCache.GetOrAdd(cacheKey, () =>
            {
                if (this._parametriLoghi.Parametri.CodiceOggettoLogoRegione.HasValue)
                {
                    return this._oggettiService.GetById(this._parametriLoghi.Parametri.CodiceOggettoLogoRegione.Value);
                }

                return BinaryFile.FromFileData("logo-regione.png", "image/png", Convert.FromBase64String(Constants.Base64Empty));
            });
        }

        public BinaryFile GetLogoAreaRiservata(Func<string, BinaryFile> loadFromUrlCallback)
        {
            var cacheKey = $"LogoAreaRiservata.{this._aliasSoftwareResolver.AliasComune}.{this._aliasSoftwareResolver.Software}";
            return this._applicationCache.GetOrAdd(cacheKey, () =>
            {
                if (!String.IsNullOrEmpty(this._parametriLoghi.Parametri.UrlLogo))
                {
                    return loadFromUrlCallback(this._parametriLoghi.Parametri.UrlLogo);
                }

                if (this._parametriLoghi.Parametri.CodiceOggettoLogoComune.HasValue)
                {
                    return this._oggettiService.GetById(this._parametriLoghi.Parametri.CodiceOggettoLogoComune.Value);
                }

                return this._risorseService.GetRisorsaFrontoffice(Constants.IdLogoStiliFrontoffice);
            });
        }

        public Task<BinaryFile> GetLogoAreaRiservataAsync(Func<string, Task<BinaryFile>> loadFromUrlCallback)
        {
            var cacheKey = $"LogoAreaRiservata.{this._aliasSoftwareResolver.AliasComune}.{this._aliasSoftwareResolver.Software}";
            return ((IAsyncCache)this._applicationCache).GetOrAddAsync(cacheKey, () => GetLogoComuneInternalAsync(loadFromUrlCallback));
        }

        private async Task<BinaryFile> GetLogoComuneInternalAsync(Func<string, Task<BinaryFile>> loadFromUrlCallback)
        {
            if (!String.IsNullOrEmpty(this._parametriLoghi.Parametri.UrlLogo))
            {
                return await loadFromUrlCallback(this._parametriLoghi.Parametri.UrlLogo);
            }

            if (this._parametriLoghi.Parametri.CodiceOggettoLogoComune.HasValue)
            {
                return await this._oggettiService.GetByIdAsync(this._parametriLoghi.Parametri.CodiceOggettoLogoComune.Value);
            }

            return this._risorseService.GetRisorsaFrontoffice(Constants.IdLogoStiliFrontoffice);
        }
    }
}
