using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using log4net;
using Ninject;
using System;
using System.Globalization;
using System.Net;
using System.Threading;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.oggetti
{
    public abstract class DownloadPageBase : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(download));
        protected HttpContext Context { get; private set; } = null;

        [Inject]
        protected IUrlDownloadOggettiService _urlDownloadService { get; set; }

        private static class Constants
        {
            public const string IdQuerystringParameter = "id";
        }

        public override bool IsReusable => false;

        protected override void DoProcessRequest(HttpContext context)
        {
            var uiCulture = new CultureInfo("it-IT");
            uiCulture.NumberFormat.NumberDecimalSeparator = ",";
            uiCulture.NumberFormat.NumberGroupSeparator = ".";
            uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
            uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

            Thread.CurrentThread.CurrentUICulture = uiCulture;
            Thread.CurrentThread.CurrentCulture = uiCulture;

            this.Context = context;

            var id = context.Request.QueryString[Constants.IdQuerystringParameter];

            if (String.IsNullOrEmpty(id))
            {
                _log.Error("Richiesta all'handler di download con id nullo");

                WriteError("id non valido");

                return;
            }

            try
            {
                var codiceOggetto = this._urlDownloadService.GetCodiceOggettoDaEncryptedString(id);

                // Imposto l'alias dell'utente loggato nel contesto http
                ContextAliasSoftwareSetter.Set(context, codiceOggetto.Alias);

                var file = GetOggetto(codiceOggetto); // this._oggettiService.GetById(codiceOggetto.Id);

                file.WriteTo(context.Response);
            }
            catch (ThreadAbortException)
            {
                // Usata response.end, non faccio nulla
            }
            catch (FileChecksumValidationException ex)
            {
                _log.Error($"Invocato handler di download con un id non valido: {id}, ex={ex}");

                WriteError("id non valido");
            }
            catch (Exception ex)
            {
                var errorId = Guid.NewGuid().ToString();

                this._log.Error($"Si è verificato un errore durante il recupero del file per l'id {id}. Riferimento errore: {errorId}, Errore: {ex}");

                WriteError($"Si è verificato un errore durante il recupero del file. Riferimento dell'errore: {errorId}", HttpStatusCode.InternalServerError);
            }
        }

        protected void WriteError(string errore, HttpStatusCode statusCode = HttpStatusCode.NotFound)
        {
            Context.Response.StatusCode = (int)statusCode;
            Context.Response.ContentType = "text/plain";
            Context.Response.Write(errore);
            Context.Response.End();
        }

        protected abstract BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto);
    }
}