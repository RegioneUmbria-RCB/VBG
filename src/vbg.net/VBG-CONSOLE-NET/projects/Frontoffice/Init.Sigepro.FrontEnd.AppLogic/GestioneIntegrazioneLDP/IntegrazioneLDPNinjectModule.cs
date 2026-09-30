using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena;
using Ninject.Modules;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Ninject.Web.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP
{
    public class IntegrazioneLDPNinjectModule : NinjectModule
    {
        public override void Load()
        {
            Bind<IDownloadPdfDomanda>().To<DownloadPdfDomanda>().InRequestScope();
            Bind<IAreeUsoPubblicoLivornoService>().To<AreeUsoPubblicoLivornoService>().InRequestScope();
            Bind<IPraticheEdilizieSienaService>().To<PraticheEdilizieSienaService>().InRequestScope();
        }
    }
}
