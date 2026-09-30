using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti
{
    public interface IUrlDownloadOggettiService
    {
        string GetUrlDownload(int codiceOggetto);

        string GetUrlDownloadPdfCompilabile(int codiceOggetto, int idDomanda, string software);

        //string GetUrlDownloadFirmato(int codiceOggetto);

        string GetUrlDownloadConvertito(int codiceOggetto, FormatoConversioneEnum formato);

        string GetUrlDownloadCompilato(int codiceOggetto, int idDomanda, FormatoConversioneEnum formato, int? idAllegatoDomandaMd5 = null);

        CodiceOggettoDownload GetCodiceOggettoDaEncryptedString(string encryptedString);
    }
}
