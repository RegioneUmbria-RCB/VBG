using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
    public interface ICondizioneUscitaGestioneAnagrafiche : ICondizioneUscitaStep
    {
        ITipiSoggettoService _tipiSoggettoService { get; set; }
        IAuthenticationDataResolver _authenticationDataResolver { get; set; }
        IConfigurazione<ParametriLogin> _parametriLogin { get; set; }

        bool FlagVerificaPecObbligatoria { get; set; }
        string MessaggioUtenteNonPresente { get; set; }
        bool VerificaPresenzaUtenteLoggato { get; set; }
    }
}