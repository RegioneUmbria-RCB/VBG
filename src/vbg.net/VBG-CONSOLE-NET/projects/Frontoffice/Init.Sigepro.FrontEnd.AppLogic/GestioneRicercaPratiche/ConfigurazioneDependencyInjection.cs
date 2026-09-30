using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject.Web.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche
{
    public static class ConfigurazioneDependencyInjection
    {
        public static ConfigurazioneAreaRiservataModule ConfiguraRicercaPratiche(this ConfigurazioneAreaRiservataModule k)
        {
            k.Bind<RicercaPraticheServiceCreator>().ToSelf().InRequestScope();
            k.Bind<IRicercaPraticheService>().To<RicercaPraticheService>().InRequestScope();
            k.Bind<CopiaDatiDomandaDaRisultatoRicercaService>().ToSelf().InRequestScope();

            return k;
        }
    }
}
