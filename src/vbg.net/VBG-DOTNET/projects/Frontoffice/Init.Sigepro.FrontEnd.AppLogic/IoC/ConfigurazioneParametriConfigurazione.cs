using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneParametriConfigurazione
    {
        public static IDIProvider ConfiguraParametriConfigurazione(this IDIProvider kernel)
        {
            kernel.AddScoped<CacheParametriSigeproSecurity>();

            kernel.AddConfigurationParameter<ParametriAspetto, ParametriAspettoBuilder>();
            kernel.AddConfigurationParameter<ParametriAccessoAtti, ParametriAccessoAttiBuilder>();
            kernel.AddConfigurationParameter<ParametriDatiCatastali, ParametriDatiCatastaliBuilder>();
            kernel.AddConfigurationParameter<ParametriInvio, ParametriInvioBuilder>();
            kernel.AddConfigurationParameter<ParametriLogin, ParametriLoginBuilder>();
            kernel.AddConfigurationParameter<ParametriMenuV2, ParametriMenuBuilder>();
            kernel.AddConfigurationParameter<ParametriStc, ParametriStcBuilder>();
            kernel.AddConfigurationParameter<ParametriVisura, ParametriVisuraBuilder>();
            kernel.AddConfigurationParameter<ParametriWorkflow, ParametriWorkflowBuilder>();
            kernel.AddConfigurationParameter<ParametriScadenzario, ParametriScadenzarioBuilder>();
            kernel.AddConfigurationParameter<ParametriRegistrazione, ParametriRegistrazioneBuilder>();
            kernel.AddConfigurationParameter<ParametriSigeproSecurity, ParametriSigeproSecurityBuilder>();
            kernel.AddConfigurationParameter<ParametriSchedaCittadiniExtracomunitari, ParametriSchedaCittadiniExtracomunitariBuilder>();
            kernel.AddConfigurationParameter<ParametriCart, ParametriCartBuilder>();
            kernel.AddConfigurationParameter<ParametriFirmaDigitale, ParametriFirmaDigitaleBuilder>();
            kernel.AddConfigurationParameter<ParametriRicercaAnagrafiche, ParametriRicercaAnagraficheBuilder>();
            kernel.AddConfigurationParameter<ParametriAllegati, ParametriAllegatiBuilder>();
            kernel.AddConfigurationParameter<ParametriLocalizzazioni, ParametriLocalizzazioniBuilder>();
            kernel.AddConfigurationParameter<ParametriIntegrazioneLDP, ParametriIntegrazioneLDPBuilder>();
            kernel.AddConfigurationParameter<ParametriIntegrazioniDocumentali, ParametriIntegrazioniDocumentaliBuilder>();
            kernel.AddConfigurationParameter<ParametriPhantomjs, ParametriPhantomjsBuilder>();
            kernel.AddConfigurationParameter<ParametriContenutiComunali, ParametriContenutiComunaliBuilder>();
            kernel.AddConfigurationParameter<ParametriLoghi, ParametriLoghiBuilder>();
            kernel.AddConfigurationParameter<ParametriLivornoConfigurazioneDrupal, ParametriLivornoConfigurazioneDrupalBuilder>();
            kernel.AddConfigurationParameter<ParametriUrlAreaRiservata, ParametriUrlAreaRiservataBuilder>();
            kernel.AddConfigurationParameter<ParametriAreaRiservata, ParametriAreaRiservataBuilder>();
            kernel.AddConfigurationParameter<ParametriGenerazioneRiepilogoDomanda, ParametriGenerazioneRiepilogoDomandaBuilder>();
            kernel.AddConfigurationParameter<ParametriARRedirect, ParametriARRedirectBuilder>();
            kernel.AddConfigurationParameter<ParametriFvgSol, ParametriFvgSolBuilder>();
            kernel.AddConfigurationParameter<ParametriDimensioneAllegatiLiberi, ParametriDimensioneAllegatiLiberiBuilder>();
            kernel.AddConfigurationParameter<ParametriScrivaniaEntiTerzi, ParametriScrivaniaEntiTerziBuilder>();
            kernel.AddConfigurationParameter<ParametriTriesteAccessoAtti, TriesteAccessoAttiBuilder>();
            kernel.AddConfigurationParameter<ParametriGenerazioneCertificatoInvio, ParametriGenerazioneCertificatoDiInvioBuilder>();
            kernel.AddConfigurationParameter<ParametriPresentazioneDomanda, ParametriPresentazioneDomandaBuilder>();
            kernel.AddConfigurationParameter<ParametriQuestionarioSoddisfazione, ParametriQuestionarioBuilder>();
            kernel.AddConfigurationParameter<ParametriSIT, ParametriSITBuilder>();
            kernel.AddConfigurationParameter<ParametriAreaRiservataCore, ParametriAreaRiservataCoreBuilder>();
            kernel.AddConfigurationParameter<ConfigurazioneRabbitMQ, ConfigurazioneRabbitMQBuilder>();
            kernel.AddConfigurationParameter<ParametriDomandaPNRR, ParametriDomandaPNRRBuiler>();
            kernel.AddConfigurationParameter<ParametriRiepilogoSingolaScheda, ParametriRiepilogoSingolaSchedaBuilder>();
            kernel.AddConfigurationParameter<ParametriPagamentiOnLine, ParametriPagamentiOnLineBuilder>();

            return kernel;
        }
    }
}
