using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole.Authentication
{
    public interface IVbgCrossLoginClient
    {
        string GetCrossLoginToken(string url, AnagraficaUtente datiUtente);
    }
}