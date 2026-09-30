using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.oggetti
{
    /// <summary>
    /// Summary description for download_pdf_compilabile
    /// </summary>
    public class download_pdf_compilabile : DownloadPageBase
    {
        [Inject]
        protected IPdfUtilsService _pdfUtilsService { get; set; }

        protected override BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto)
        {
            var idDomanda = new QsIdDomandaOnline(Context.Request.QueryString);

            var file = _pdfUtilsService.PrecompilaPdf(codiceOggetto.Id, idDomanda.Value);

            return file;

        }
    }
}