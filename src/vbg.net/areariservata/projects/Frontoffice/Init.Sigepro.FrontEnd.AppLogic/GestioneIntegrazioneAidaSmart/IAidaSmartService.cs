using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneAidaSmart
{
    public interface IAidaSmartService
    {
        string GetUrlNuovaDomanda(AnagraficaUtente utenteCorrente);
        string GetUrlIstanzeInSospeso(AnagraficaUtente utenteCorrente);
    }
}