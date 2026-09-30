using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;

namespace Init.Sigepro.FrontEnd.oggetti
{
    public class download : DownloadPageBase
    {
        [Inject]
        protected IOggettiService _oggettiService { get; set; }

        protected override BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto)
        {
            return this._oggettiService.GetById(codiceOggetto.Id);
        }
    }
}