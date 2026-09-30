// -----------------------------------------------------------------------
// <copyright file="DomandaOnlineEventBusBootstrapper.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
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
