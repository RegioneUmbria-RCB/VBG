using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.oggetti
{
    /// <summary>
    /// Summary description for download_multiformato
    /// </summary>
    public class download_multiformato : DownloadPageBase
    {
        [Inject]
        public IOggettiService _oggettiService { get; set; }
        [Inject]
        public FileConverterService _fileConverterService { get; set; }

        private static class Constants
        {
            public const string FormatoQuerystringKey = "f";
        }

        protected override BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto)
        {
            if (!Enum.TryParse(Context.Request.QueryString[Constants.FormatoQuerystringKey], out FormatoConversioneEnum formato))
            {
                WriteError("Formato non valido", System.Net.HttpStatusCode.InternalServerError);
                return null;
            }

            return _fileConverterService.Converti(codiceOggetto.Id, this._oggettiService, formato);
        }
    }
}