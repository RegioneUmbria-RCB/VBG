using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using Init.Sigepro.FrontEnd.Pagamenti.ESED;
using Ninject;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneIntegrazionePagamenti
    {
        public static IKernel RegistraIntegrazionePagamenti(this IKernel k)
        {
            k.Bind<IPagamentiSettingsReader>().To<PagamentiMipSettingsReader>();
            k.Bind<IPagamentiEntraNextSettingsReader>().To<PagamentiEntraNextSettingsReader>();
            
            // esed
            k.Bind<IGetStatoPagamento>().To<GetStatoPagamentoESED>().InRequestScope();
            k.Bind<PayServerClientWrapperESED>().ToMethod(x =>
            {

                var settingsReader =x.Kernel.Get<IPagamentiSettingsReader>();
                var urlEncoder = x.Kernel.Get<IUrlEncoder>();
                var getStatoPagamento = x.Kernel.Get<IGetStatoPagamento>();

                return new PayServerClientWrapperESED(new PayServerClientSettings(settingsReader.GetSettings()), urlEncoder, getStatoPagamento);
            }).InRequestScope();

            return k;
        }
    }
}
