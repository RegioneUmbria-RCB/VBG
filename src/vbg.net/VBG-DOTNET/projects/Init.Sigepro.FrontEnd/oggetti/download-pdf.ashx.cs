using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;

namespace Init.Sigepro.FrontEnd.oggetti
{
    /// <summary>
    /// Summary description for download_pdf
    /// </summary>
    public class download_pdf : DownloadPageBase
    {
        [Inject]
        protected IOggettiService _oggettiService { get; set; }
        [Inject]
        protected FileConverterService _fileConverterService { get; set; }

        protected override BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto)
        {
            return _fileConverterService.Converti(codiceOggetto.Id, _oggettiService, FormatoConversioneEnum.PDF);
        }
    }
}