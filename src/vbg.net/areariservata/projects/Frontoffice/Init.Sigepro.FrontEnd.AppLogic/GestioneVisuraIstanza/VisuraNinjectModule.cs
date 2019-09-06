using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro;
using Ninject.Modules;
using Ninject.Web.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public class VisuraNinjectModule : NinjectModule
    {
        public override void Load()
        {
            Bind<IVisuraService>().To<VisuraSigeproService>().InRequestScope();
            Bind<WsDettaglioPraticaRepository>().ToSelf().InRequestScope();
            Bind<IstanzeServiceCreator>().ToSelf().InRequestScope();
        }
    }
}
