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

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class DomandaOnlineEventBusBootstrapper
	{
		public static void Bootstrap()
		{
			var kernel = FoKernelContainer.Kernel;

			// event dispatching
			kernel.Bind<EventsBus>().ToSelf().InSingletonScope();
			kernel.Bind<ICommandSender>().ToMethod(x => (ICommandSender)x.Kernel.GetService(typeof(EventsBus)));
			kernel.Bind<IEventPublisher>().ToMethod( x => (IEventPublisher)x.Kernel.GetService(typeof(EventsBus)));
			kernel.Bind<IEventDispatcher>().To<EventDispatcher>().InSingletonScope();
		}
	}
}
