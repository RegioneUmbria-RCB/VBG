using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneDocumentiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Utils;
using Ninject;
using System;
using System.Globalization;
using System.Text;
using System.Threading;
using System.Web;
using System.Web.Services;
using System.Web.SessionState;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class DownloadOggettoCompilabile : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        private static class Constants
        {
            public const string FormatoDefault = "PDF";
        }

        private static class QuerystringConstants
        {
            public const string UserAuthenticationResult = "UserAuthenticationResult";
            public const string Software = "Software";
            public const string IdDomanda = "IdPresentazione";
            public const string PdfSchedeNf = "PdfSchedeNf";
            public const string CodiceOggetto = "CodiceOggetto";
            public const string Fmt = "Fmt";
            public const string AsAttachment = "AsAttachment";
            public const string Md5 = "Md5";
            public const string DumpXml = "DumpXml";
        }


        [Inject]
        public IOggettiService _oggettiService { get; set; }
        [Inject]
        public FileConverterService _fileConverterService { get; set; }
        [Inject]
        public DomandeOnlineService __domandeOnlineService { get; set; }
        [Inject]
        public DocumentiService _allegatiService { get; set; }
        [Inject]
        public ISostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService { get; set; }

        // [Inject]
        // public GenerazioneRiepilogoDomandaService _generazioneriepilogoService { get; set; }
        [Inject]
        public GenerazioneDocumentoDomandaService _generazioneDocumentoDomandaService { get; set; }

        public UserAuthenticationResult UserAuthenticationResult
        {
            get { return HttpContext.Current.Items[QuerystringConstants.UserAuthenticationResult] as UserAuthenticationResult; }
        }

        public string IdComune
        {
            get { return this.UserAuthenticationResult.Alias; }
        }

        public string Software
        {
            get { return HttpContext.Current.Request.QueryString[QuerystringConstants.Software]; }
        }

        public int IdPresentazione
        {
            get { return Convert.ToInt32(HttpContext.Current.Request.QueryString[QuerystringConstants.IdDomanda]); }
        }

        protected bool PdfSchedeNf
        {
            get
            {
                var _pdfSchedeNf = HttpContext.Current.Request.QueryString[QuerystringConstants.PdfSchedeNf];

                if (string.IsNullOrEmpty(_pdfSchedeNf))
                    return true;

                return Convert.ToBoolean(_pdfSchedeNf);
            }
        }

        private DomandaOnline _domandaCorrente;
        protected DomandaOnline DomandaCorrente
        {
            get
            {
                if (this._domandaCorrente == null)
                    this._domandaCorrente = this.__domandeOnlineService.GetById(this.IdPresentazione);

                return this._domandaCorrente;
            }
        }

        protected bool DumpXml
        {
            get
            {
                var qs = HttpContext.Current.Request.QueryString[QuerystringConstants.DumpXml];
                return !String.IsNullOrEmpty(qs) && qs.ToUpper() == "TRUE";
            }
        }

        protected override void DoProcessRequest(HttpContext context)
        {
            using (CodeProfiler.Track("DownoadOggettoCompilabile.DoProcessRequest"))
            {
                CultureInfo uiCulture = new CultureInfo("it-IT");
                uiCulture.NumberFormat.NumberDecimalSeparator = ",";
                uiCulture.NumberFormat.NumberGroupSeparator = ".";
                uiCulture.NumberFormat.CurrencyDecimalSeparator = ",";
                uiCulture.NumberFormat.CurrencyGroupSeparator = ".";

                Thread.CurrentThread.CurrentUICulture = uiCulture;
                Thread.CurrentThread.CurrentCulture = uiCulture;


                var codiceOggetto = Convert.ToInt32(context.Request.QueryString[QuerystringConstants.CodiceOggetto]);
                var formato = context.Request.QueryString[QuerystringConstants.Fmt];
                var asAttachmentStr = context.Request.QueryString[QuerystringConstants.AsAttachment];
                var generaMd5str = context.Request.QueryString[QuerystringConstants.Md5];

                var asAttachment = true;

                if (String.IsNullOrEmpty(formato))
                    formato = Constants.FormatoDefault;

                if (!String.IsNullOrEmpty(asAttachmentStr))
                    asAttachment = Convert.ToBoolean(asAttachmentStr);

                try
                {
                    var riepilogo = this._generazioneDocumentoDomandaService.GeneraDocumento(codiceOggetto, this.IdPresentazione, FormatoConversioneEnum.PDF);

                    if (!String.IsNullOrEmpty(generaMd5str))
                    {
                        var idAllegato = Convert.ToInt32(generaMd5str);
                        this._allegatiService.SalvaEImpostaMd5(this.DomandaCorrente.DataKey.IdPresentazione, idAllegato, riepilogo);
                    }

                    context.Response.Clear();
                    context.Response.ContentType = riepilogo.MimeType;
                    context.Response.ContentEncoding = Encoding.Default;

                    if (asAttachment)
                        context.Response.AddHeader("content-disposition", "attachment; filename=" + riepilogo.FileName + ";");

                    context.Response.BinaryWrite(riepilogo.FileContent);
                    context.Response.End();

                }
                catch (ThreadAbortException)
                {
                }
                catch (Exception ex)
                {
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
