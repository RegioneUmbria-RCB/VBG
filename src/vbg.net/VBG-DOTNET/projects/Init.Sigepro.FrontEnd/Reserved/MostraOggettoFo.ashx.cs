namespace Init.Sigepro.FrontEnd.Reserved
{
    using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
    using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
    using Ninject;
    using System;
    using System.Web;
    using System.Web.Services;
    using System.Web.SessionState;

    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class MostraOggettoFo : Ninject.Web.HttpHandlerBase, IRequiresSessionState
    {
        [Inject]
        public FileConverterService _fileConverterService { get; set; }

        [Inject]
        public IVerificaFirmaDigitaleService _firmaDigitaleService { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggetti { get; set; }

        [Inject]
        public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }


        protected override void DoProcessRequest(HttpContext context)
        {

            throw new Exception("No");

            //         var uar = (UserAuthenticationResult)context.Items["UserAuthenticationResult"];

            //         var idComune = new QsAliasComune(context.Request.QueryString).Value;
            //         var software = new QsSoftware(context.Request.QueryString).Value;
            //         var idPresentazione = new QsIdDomandaOnline(context.Request.QueryString).Value;
            //         var codiceOggetto = new QsCodiceOggetto(context.Request.QueryString).Value;
            //         var strShowInline = context.Request.QueryString["inline"];
            //         var inline = !String.IsNullOrEmpty(strShowInline) && strShowInline == "1";

            //         var convert = context.Request.QueryString[PathUtils.UrlParameters.Convert];

            //         var oggetto = this._allegatiDomandaFoRepository.LeggiAllegato(idPresentazione, codiceOggetto);

            //         if (oggetto == null)
            //         {
            //             context.Response.ContentType = "text/plain";
            //             context.Response.Write("Errore: Oggetto " + codiceOggetto + "non trovato");
            //             context.Response.End();
            //             return;
            //         }

            //         var contenutoFile = oggetto.FileContent;
            //         var mimeType = oggetto.MimeType;
            //         var nomeFile = oggetto.FileName;

            //         var esitoVerificaFirma = this._firmaDigitaleService.VerificaFirmaDigitale(oggetto);

            //         if (esitoVerificaFirma.Stato == StatoVerificaFirma.FirmaValida)
            //         {
            //             string url = this._urlDownloadOggetti.GetUrlDownloadFirmato(codiceOggetto);

            //             context.Response.Redirect(url);
            //             return;
            //         }
            //         /*
            //if (!string.IsNullOrEmpty(convert))
            //{
            //	var res = _fileConverterService.Converti(nomeFile, contenutoFile, convert);

            //	contenutoFile = res.FileContent;
            //	mimeType = res.MimeType;
            //	nomeFile = Path.GetFileNameWithoutExtension(nomeFile) + Path.GetExtension(res.FileName);
            //}
            //*/
            //         try
            //         {
            //             if (!inline)
            //             {
            //                 context.Response.AddHeader("content-disposition", "attachment;filename=\"" + nomeFile + "\"");
            //             }

            //             context.Response.ContentType = mimeType;

            //             context.Response.BinaryWrite(contenutoFile);
            //         }
            //         catch (Exception ex)
            //         {
            //             context.Response.ContentType = "text/plain";
            //             context.Response.Write("Errore: " + ex.Message);
            //         }
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
