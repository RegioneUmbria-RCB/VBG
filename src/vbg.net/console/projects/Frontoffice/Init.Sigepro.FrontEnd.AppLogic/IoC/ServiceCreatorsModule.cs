using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Ninject.Modules;
using Ninject.Web.Common;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.STC;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	public class ServiceCreatorsModule : NinjectModule
	{
		public override void Load()
		{
			Bind<StcToken>().ToSelf();
			Bind<OggettiServiceCreator>().ToSelf();
			Bind<SigeproSecurityProxy>().ToSelf();
            Bind<IStcServiceCreator>().To<StcServiceCreator>().InRequestScope();
		}
	}
}
