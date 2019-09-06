using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneParametriConfigurazione
    {
        public static IKernel RegistraParametriConfigurazione(this IKernel kernel)
        {
            kernel.Bind<IConfigurazioneBuilder<ParametriAspetto>>().To<ParametriAspettoBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriDatiCatastali>>().To<ParametriDatiCatastaliBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriInvio>>().To<ParametriInvioBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriLogin>>().To<ParametriLoginBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriMenuV2>>().To<ParametriMenuBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriStc>>().To<ParametriStcBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriVisura>>().To<ParametriVisuraBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriWorkflow>>().To<ParametriWorkflowBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriSigeproSecurity>>().To<ParametriSigeproSecurityBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriScadenzario>>().To<ParametriScadenzarioBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriRegistrazione>>().To<ParametriRegistrazioneBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriSchedaCittadiniExtracomunitari>>().To<ParametriSchedaCittadiniExtracomunitariBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriCart>>().To<ParametriCartBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriFirmaDigitale>>().To<ParametriFirmaDigitaleBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriRicercaAnagrafiche>>().To<ParametriRicercaAnagraficheBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriAllegati>>().To<ParametriAllegatiBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriConfigurazionePagamentiMIP>>().To<ParametriPagamentiMipServiceBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriConfigurazionePagamentiEntraNext>>().To<ParametriPagamentiEntraNextServiceBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriLocalizzazioni>>().To<ParametriLocalizzazioniBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriIntegrazioneLDP>>().To<ParametriIntegrazioneLDPBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriIntegrazioniDocumentali>>().To<ParametriIntegrazioniDocumentaliBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriPhantomjs>>().To<ParametriPhantomjsBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriLoghi>>().To<ParametriLoghiBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriLivornoConfigurazioneDrupal>>().To<ParametriLivornoConfigurazioneDrupalBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriUrlAreaRiservata>>().To<ParametriUrlAreaRiservataBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriGenerazioneRiepilogoDomanda>>().To<ParametriGenerazioneRiepilogoDomandaBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriARRedirect>>().To<ParametriARRedirectBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriAidaSmart>>().To<ParametriAidaSmartBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriFvgSol>>().To<ParametriFvgSolBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriDimensioneAllegatiLiberi>>().To<ParametriDimensioneAllegatiLiberiBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriScrivaniaEntiTerzi>>().To<ParametriScrivaniaEntiTerziBuilder>().InRequestScope();
            kernel.Bind<IConfigurazioneBuilder<ParametriTriesteAccessoAtti>>().To<TriesteAccessoAttiBuilder>().InRequestScope();



            kernel.Bind<IConfigurazione<ParametriAspetto>>().To<ConfigurazioneImpl<ParametriAspetto>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriDatiCatastali>>().To<ConfigurazioneImpl<ParametriDatiCatastali>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriInvio>>().To<ConfigurazioneImpl<ParametriInvio>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriLogin>>().To<ConfigurazioneImpl<ParametriLogin>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriMenuV2>>().To<ConfigurazioneImpl<ParametriMenuV2>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriStc>>().To<ConfigurazioneImpl<ParametriStc>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriVisura>>().To<ConfigurazioneImpl<ParametriVisura>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriWorkflow>>().To<ConfigurazioneImpl<ParametriWorkflow>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriScadenzario>>().To<ConfigurazioneImpl<ParametriScadenzario>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriRegistrazione>>().To<ConfigurazioneImpl<ParametriRegistrazione>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriSigeproSecurity>>().To<ConfigurazioneImpl<ParametriSigeproSecurity>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriSchedaCittadiniExtracomunitari>>().To<ConfigurazioneImpl<ParametriSchedaCittadiniExtracomunitari>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriCart>>().To<ConfigurazioneImpl<ParametriCart>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriFirmaDigitale>>().To<ConfigurazioneImpl<ParametriFirmaDigitale>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriRicercaAnagrafiche>>().To<ConfigurazioneImpl<ParametriRicercaAnagrafiche>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriAllegati>>().To<ConfigurazioneImpl<ParametriAllegati>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriConfigurazionePagamentiMIP>>().To<ConfigurazioneImpl<ParametriConfigurazionePagamentiMIP>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriConfigurazionePagamentiEntraNext>>().To<ConfigurazioneImpl<ParametriConfigurazionePagamentiEntraNext>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriLocalizzazioni>>().To<ConfigurazioneImpl<ParametriLocalizzazioni>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriIntegrazioneLDP>>().To<ConfigurazioneImpl<ParametriIntegrazioneLDP>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriIntegrazioniDocumentali>>().To<ConfigurazioneImpl<ParametriIntegrazioniDocumentali>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriPhantomjs>>().To<ConfigurazioneImpl<ParametriPhantomjs>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriLoghi>>().To<ConfigurazioneImpl<ParametriLoghi>>().InRequestScope(); ;
            kernel.Bind<IConfigurazione<ParametriLivornoConfigurazioneDrupal>>().To<ConfigurazioneImpl<ParametriLivornoConfigurazioneDrupal>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriUrlAreaRiservata>>().To<ConfigurazioneImpl<ParametriUrlAreaRiservata>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriGenerazioneRiepilogoDomanda>>().To<ConfigurazioneImpl<ParametriGenerazioneRiepilogoDomanda>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriARRedirect>>().To<ConfigurazioneImpl<ParametriARRedirect>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriAidaSmart>>().To<ConfigurazioneImpl<ParametriAidaSmart>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriFvgSol>>().To<ConfigurazioneImpl<ParametriFvgSol>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriDimensioneAllegatiLiberi>>().To<ConfigurazioneImpl<ParametriDimensioneAllegatiLiberi>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriScrivaniaEntiTerzi>>().To<ConfigurazioneImpl<ParametriScrivaniaEntiTerzi>>().InRequestScope();
            kernel.Bind<IConfigurazione<ParametriTriesteAccessoAtti>>().To<ConfigurazioneImpl<ParametriTriesteAccessoAtti>>().InRequestScope();

            return kernel;
        }
    }
}
