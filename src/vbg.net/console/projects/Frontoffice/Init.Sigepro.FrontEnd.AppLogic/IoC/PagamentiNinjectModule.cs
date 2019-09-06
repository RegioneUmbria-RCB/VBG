using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Init.Sigepro.FrontEnd.Pagamenti;
using Ninject.Modules;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public class PagamentiNinjectModule : NinjectModule
    {
        public override void Load()
        {
            Bind<IPagamentiSettingsReader>().To<PagamentiMipSettingsReader>();
        }
    }
}
