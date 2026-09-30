using Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
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

        private HttpContext _context;
        private static readonly byte[] _base64Empty = Convert.FromBase64String(Constants.Base64Empty);
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
            var file = this._loghiService.GetLogoRegione();
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