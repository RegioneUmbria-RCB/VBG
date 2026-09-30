using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using Ninject;
using Ninject.Web.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.IoC
{
    public static class ConfigurazioneIntegrazionePagamenti
    {
        public static IKernel RegistraIntegrazionePagamenti(this IKernel k)
        {
            // NODO_PAGAMENTI
            k.Bind<INodoPagamentiSettingsReader>().To<NodoPagamentiSettingsReader>().InRequestScope();
            k.Bind<IPagamentiNodoPagamentiService>().To<PagamentiNodoPagamentiService>().InRequestScope();
            k.Bind<IConfigurazioneNodoPagamentiRepository>().To<ConfigurazioneNodoPagamentiRepository>().InRequestScope();
            k.Bind<IVerificaConfigurazioneNodoPagamentiService>().To<VerificaConfigurazioneNodoPagamentiService>().InRequestScope();
            // k.Bind<IConfigurazioneBuilder<ParametriConfigurazionePagamentiNodoPagamenti>>().To<ParametriPagamentiNodoPagamentiServiceBuilder>().InRequestScope();
            k.Bind<INodoPagamentiPaymentService>().To<NodoPagamentiPaymentService>().InRequestScope();
            k.Bind<IContiRepository>().To<ContiRepository>();
            k.Bind<IEstremiDomandaNodoPagamentiReader>().To<EstremiDomandaNodoPagamentiReader>().InRequestScope();
            k.Bind<OneriServiceCreator>().ToSelf().InTransientScope();

            return k;
        }
    }
}
