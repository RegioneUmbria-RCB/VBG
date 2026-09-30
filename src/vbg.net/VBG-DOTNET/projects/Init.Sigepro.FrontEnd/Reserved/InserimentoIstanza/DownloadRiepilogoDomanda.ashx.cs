using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Utils;
using log4net;
using Ninject;
using System;
using System.Globalization;
using System.Text;
using System.Threading;
using System.Web;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    /// <summary>
    /// Summary description for DownloadRiepilogoDomanda
    /// </summary>
    public class DownloadRiepilogoDomanda : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        private static class QuerystringConstants
        {
            // public const string UserAuthenticationResult = "UserAuthenticationResult";
            // public const string Software = "Software";

            // public const string CodiceOggetto = "CodiceOggetto";
            // public const string Fmt = "Fmt";
            // public const string AsAttachment = "AsAttachment";
            // public const string Md5 = "Md5";
            public const string IdDomanda = "IdDomanda";
            public const string DumpXml = "DumpXml";
            public const string PdfSchedeNf = "PdfSchedeNf";
            public const string DownloadAsAttachment = "AsAttachment";

        }

        [Inject]
        public GenerazioneRiepilogoDomandaLegacyService _generazioneRiepilogoDomandaService { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(DownloadRiepilogoDomanda));

        protected bool DumpXml
        {
            get
            {
                var qs = HttpContext.Current.Request.QueryString[QuerystringConstants.DumpXml];
                return !String.IsNullOrEmpty(qs) && qs.ToUpper() == "TRUE";
            }
        }

        protected bool InserisciSchedeNonFirmate
        {
            get
            {
                var qs = HttpContext.Current.Request.QueryString[QuerystringConstants.PdfSchedeNf];
                return (qs?.ToUpper() ?? "") != "FALSE";
            }
        }

        protected bool DownloadAsAttachment
        {
            get
            {
                var qs = HttpContext.Current.Request.QueryString[QuerystringConstants.DownloadAsAttachment];
                return !String.IsNullOrEmpty(qs) && (qs.ToUpper() == "TRUE" || qs == "1");
            }
        }



        protected override void DoProcessRequest(HttpContext context)
        {
            var idDomanda = new QsIdDomandaOnline(context.Request.QueryString).Value;


            this._log.DebugFormat("Generazione del riepilogo per la domanda {0}. Inserisci schede: {1}, Dump xml: {2}", idDomanda, this.InserisciSchedeNonFirmate, this.DumpXml);

            using (CodeProfiler.Track("DownloadRiepilogoDomanda.DoProcessRequest"))
            {
                var uiCulture = new CultureInfo("it-IT");
                uiCulture.NumberFormat.NumberDecimalSeparator = ",";
                uiCulture.NumberFormat.NumberGroupSeparator = ".";
                uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
                uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

                Thread.CurrentThread.CurrentUICulture = uiCulture;
                Thread.CurrentThread.CurrentCulture = uiCulture;

                try
                {
                    var riepilogo = this._generazioneRiepilogoDomandaService.GeneraRiepilogoDomanda(idDomanda, this.InserisciSchedeNonFirmate, this.DumpXml);

                    this._log.DebugFormat("Riepilogo domanda generato con successo");

                    context.Response.Clear();
                    context.Response.ContentType = riepilogo.MimeType;
                    context.Response.ContentEncoding = Encoding.Default;

                    if (this.DownloadAsAttachment)
                        context.Response.AddHeader("content-disposition", "attachment; filename=\"" + riepilogo.FileName + "\";");

                    context.Response.BinaryWrite(riepilogo.FileContent);
                    context.Response.End();
                }
                catch (ThreadAbortException)
                {
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Errore durante la generazione del riepilogo per la domanda con id {0}: {1}", idDomanda, ex.ToString());

                    context.Response.ContentType = "text/plain";
                    context.Response.Write("Errore: " + ex.Message);
                }
            }
        }

        public override bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}