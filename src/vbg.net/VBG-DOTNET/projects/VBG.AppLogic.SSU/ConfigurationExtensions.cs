using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.STC.Configuration;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;
using VBG.AppLogic.SSU.Configurazione;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.GestioneAllegati;
using VBG.AppLogic.SSU.GestioneCorrezioni;
using VBG.AppLogic.SSU.GestioneDatiDinamici;
using VBG.AppLogic.SSU.GestioneEventiDellaVita;
using VBG.AppLogic.SSU.GestioneFattispecie;
using VBG.AppLogic.SSU.GestioneIstanzaRifiutata;
using VBG.AppLogic.SSU.GestioneOneri;
using VBG.AppLogic.SSU.GestioneProcedimenti;
using VBG.AppLogic.SSU.GestioneRichiesteIntegrazioni;
using VBG.AppLogic.SSU.GestioneRiepilogoDomanda;
using VBG.AppLogic.SSU.GestioneTipiSoggetto;
using VBG.AppLogic.SSU.GestioneTipologie;
using VBG.AppLogic.SSU.InvioDomanda;
using VBG.AppLogic.SSU.MapperValidator;
using VBG.AppLogic.SSU.OperazioniPostInvio;
using VBG.AppLogic.SSU.STC;

namespace VBG.AppLogic.SSU
{
    public static class ConfigurationExtensions
    {
        public static IDIProvider ConfiguraIntegrazioneSsu(this IDIProvider kernel)
        {
            kernel.AddConfigurationParameter<ParametriSsu, ParametriSsuBuilder>();

            kernel.AddScoped<SsuCatalogoServiziClient>();
            kernel.AddScoped<SsuEventiDellaVitaService>();
            kernel.AddScoped<SsuTipologieService>();
            kernel.AddScoped<SsuFattispecieService>();
            kernel.AddScoped<SsuProcedimentiService>();
            kernel.AddScoped<SsuProcedimentiDomandaService>();
            kernel.AddScoped<SsuAllegatiService>();
            kernel.AddScoped<SsuTipiSoggettoService>();
            kernel.AddScoped<SsuOneriService>();
            kernel.AddScoped<SsuDatiDinamiciService>();
            kernel.AddScoped<SsuDatiDinamiciDomandaSyncService>();
            kernel.AddScoped<SsuDatiDinamiciLoader>();
            kernel.AddScoped<SsuRiepilogoDomandaService>();


            // Validator
            kernel.AddScoped<IValidatorApiClient, ValidatorApiClient>();
            kernel.AddScoped<SsuValidatorService>();

            // Aggiungere qui la registrazione degli adapter di STC
            kernel.AddScoped<ProcedimentiSsuPartialAdapter>();
            kernel.AddScoped<DatiDinamiciSsuPartialAdapter>();
            kernel.AddScoped<ICondizioneAttivazioneDatiDinamiciAdapter, CondizioneAttivazioneDatiDinamiciAdapter>();
            kernel.RegistraStcAdapter<SsuPartialAdapter>();

            // Accesso ai dati
            kernel.AddScoped<DomandeSsuRepository>();
            kernel.AddScoped<IDomandeSsuRepository, DomandeSsuRepository>();
            kernel.AddScoped<FoDomandeCorrezioniRepository>();
            kernel.AddScoped<FoDomandeIntegrazioniRepository>();

            // invio
            kernel.AddScoped<SsuInvioDomandaService>();
            kernel.AddScoped<SsuOperazioniPostInvioService>();

            // Gestione correzioni
            kernel.AddScoped<IGestioneCorrezioniSsuService, GestioneCorrezioniSsuService>();
            kernel.AddScoped<NotificaCorrezioneStcService>();

            // Gestione richieste integrazione
            kernel.AddScoped<IGestioneRichiestaIntegrazioniService, GestioneRichiestaIntegrazioniService>();

            // Istanza rifiutata
            kernel.AddScoped<IGestioneIstanzaRifiutataSsuService, GestioneIstanzaRifiutataSsuService>();

            return kernel;
        }
    }
}
