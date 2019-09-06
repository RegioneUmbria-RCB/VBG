// -----------------------------------------------------------------------
// <copyright file="DomandaOnlineEventBusBootstrapper.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Ninject.Web.Common;
    using Init.Sigepro.FrontEnd.Infrastructure.IOC;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public static class DomandaOnlineEventBusBootstrapper
	{
		public static void Bootstrap()
		{
			var kernel = FoKernelContainer.Kernel;

			// event dispatching
            //kernel.Bind<EventsBus>().ToSelf().InRequestScope();
            //kernel.Bind<ICommandSender>().ToMethod(x => (ICommandSender)x.Kernel.GetService(typeof(EventsBus))).InRequestScope(); ;
            //kernel.Bind<IEventPublisher>().ToMethod(x => (IEventPublisher)x.Kernel.GetService(typeof(EventsBus))).InRequestScope(); ;
            //kernel.Bind<IEventDispatcher>().To<EventDispatcher>().InRequestScope();
		}
	}
}
