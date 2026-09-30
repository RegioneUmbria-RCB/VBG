using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
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

        private HttpContext _context;
        [Inject]
        protected LoghiAreaRiservataService _loghiService { get; set; }

        private string IdComune
        {
            get
            {
                return this._context.Request.QueryString[Constants.IdComune];
            }
        }

        private string Software
        {
            get
            {
                return this._context.Request.QueryString[Constants.Software];
            }
        }

        public void ProcessRequest(HttpContext context)
        {
            this._context = context;
            FoKernelContainer.Inject(this);
            var file = this._loghiService.GetLogoAreaRiservata(url =>
            {
                using (var wc = new WebClient())
                {
                    var fileContent = wc.DownloadData(url);
                    return BinaryFile.FromFileData("logo.png", "image/png", fileContent);
                }
            });
            context.Response.AddHeader("Content-Disposition", "inline");
            context.Response.ContentType = file.MimeType;
            context.Response.BinaryWrite(file.FileContent);
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