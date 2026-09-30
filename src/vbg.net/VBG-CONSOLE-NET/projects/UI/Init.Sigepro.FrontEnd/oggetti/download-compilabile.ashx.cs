using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneDocumentiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.Service;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.oggetti
{
    /// <summary>
    /// Summary description for download_compilabile
    /// </summary>
    public class download_compilabile : DownloadPageBase
    {
        private static class Constants
        {
            public const string FormatoQuerystringKey = "f";
            public const string Md5AllegatoQuerystringKey = "all";
        }

        [Inject]
        public GenerazioneDocumentoDomandaService _generazioneDocumentoDomandaService { get; set; }
        [Inject]
        public DocumentiService _allegatiService { get; set; }

        protected override BinaryFile GetOggetto(CodiceOggettoDownload codiceOggetto)
        {
            var idDomanda = new QsIdDomandaOnline(Context.Request.QueryString);

            if (!Enum.TryParse(Context.Request.QueryString[Constants.FormatoQuerystringKey], out FormatoConversioneEnum formato))
            {
                WriteError("Formato non valido", System.Net.HttpStatusCode.InternalServerError);
                return null;
            }

            var idAllegatoMd5 = Context.Request.QueryString[Constants.Md5AllegatoQuerystringKey];

            var riepilogo = _generazioneDocumentoDomandaService.GeneraDocumento(codiceOggetto.Id, idDomanda.Value, formato);

            if (!String.IsNullOrEmpty(idAllegatoMd5))
            {
                var idAllegato = Convert.ToInt32(idAllegatoMd5);
                _allegatiService.SalvaEImpostaMd5(idDomanda.Value, idAllegato, riepilogo);
            }

            return riepilogo;
        }
    }
}