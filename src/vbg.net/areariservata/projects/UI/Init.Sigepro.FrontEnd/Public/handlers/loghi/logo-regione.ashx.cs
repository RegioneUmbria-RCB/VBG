using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Public.handlers.loghi
{
    /// <summary>
    /// Summary description for logo_regione
    /// </summary>
    public class logo_regione : IHttpHandler, IRequiresSessionState
    {
        private static class Constants
        {
            public const string Software = "Software";
            public const string IdComune = "IdComune";
            public const string Base64Empty = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjAuOWwzfk4AAAANSURBVBhXY/j//z8DAAj8Av6IXwbgAAAAAElFTkSuQmCC";
        }

        HttpContext _context;
        static byte[] _base64Empty = Convert.FromBase64String(Constants.Base64Empty);

        [Inject]
        protected IOggettiService _oggettiService { get; set; }

        [Inject]
        protected IConfigurazione<ParametriLoghi> _parametriLoghi { get; set; }

        [Inject]
        protected ApplicationLevelCacheHelper _cache { get; set; }

        private string IdComune
        {
            get
            {
                return _context.Request.QueryString[Constants.IdComune];
            }
        }

        private string Software
        {
            get
            {
                return _context.Request.QueryString[Constants.Software];
            }
        }


        public void ProcessRequest(HttpContext context)
        {
            this._context = context;

            FoKernelContainer.Inject(this);

            var file = GetLogo();

            context.Response.ContentType = file.MimeType;
            context.Response.BinaryWrite(file.FileContent);
        }

        private BinaryFile GetLogo()
        {
            var cacheKey = $"logo.regione.{this.IdComune}.{this.Software}";

            return this._cache.GetOrAdd(cacheKey, () =>
            {
                if (this._parametriLoghi.Parametri.CodiceOggettoLogoRegione.HasValue)
                {
                    return this._oggettiService.GetById(this._parametriLoghi.Parametri.CodiceOggettoLogoRegione.Value);
                }

                return BinaryFile.FromFileData("logo-regione.png", "image/png", _base64Empty);
            });
        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}