using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Public.handlers.loghi
{
    /// <summary>
    /// Summary description for logo_area_riservata
    /// </summary>
    public class logo_area_riservata : IHttpHandler, IRequiresSessionState
    {
        private static class Constants
        {
            public const string Software = "Software";
            public const string IdComune = "IdComune";
        }

        HttpContext _context;

        [Inject]
        protected LoghiAreaRiservataService _loghiService { get; set; }

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

            context.Response.Headers.Add("Content-Disposition", "inline");
            context.Response.ContentType = file.MimeType;
            context.Response.BinaryWrite(file.FileContent);
        }

        private BinaryFile GetLogo()
        {
            var cacheKey = $"logo.area.riservata.{this.IdComune}.{this.Software}";

            return this._cache.GetOrAdd(cacheKey, () =>
            {
                if (!String.IsNullOrEmpty(this._parametriLoghi.Parametri.UrlLogo))
                {
                    return LoadFileFromUrl(this._parametriLoghi.Parametri.UrlLogo);
                }

                if (this._parametriLoghi.Parametri.CodiceOggettoLogoComune.HasValue)
                {
                    return this._oggettiService.GetById(this._parametriLoghi.Parametri.CodiceOggettoLogoComune.Value);
                }

                return this._loghiService.GetLogoAreaRiservata(Software);
            });




        }

        [DebuggerStepThrough]
        private BinaryFile LoadFileFromUrl(string url)
        {
            using(var wc = new WebClient())
            {
                var fileContent = wc.DownloadData(url);

                return BinaryFile.FromFileData("logo.png", "image/png", fileContent);
            }
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