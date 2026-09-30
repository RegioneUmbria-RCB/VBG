namespace Init.Sigepro.FrontEnd.AppLogic.Services.Navigation
{
    public interface IRedirectService
    {
        void RedirectToAvvisoInternetExplorer();
        void RedirectToHomeAreaRiservata(string returnTo = "");
        void RedirectToHomeContenuti();
        void RedirectToLogoutUrl();
        void RedirectToPaginaCompilazioneOggetti(int idPresentazione, int idAllegato, string tipoAllegato);
        void RedirectToUrlRegistrazioneCompletata();
        void RedirectToUrlRegistrazioneCompletataCie();
        void ToFirmaDigitale(int idDomanda, int codiceOggetto);
        void ToUploadAllegatiMultipli(int idDomanda, string origine, int idAllegato);
    }
}