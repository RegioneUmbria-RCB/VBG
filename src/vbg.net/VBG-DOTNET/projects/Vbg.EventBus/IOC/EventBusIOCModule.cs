using Vbg.EventBus.Abstractions;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Vbg.EventBus.IOC
{
    public static class EventBusIOCModule
    {
        public static IDIProvider RegistraEventBusModule(this IDIProvider p)
        {
            p.AddSingleton<EventSubscribersRegistry>();

            p.AddTransient<IEventPublisher>((ctxt) =>
            {
                var registry = ctxt.GetService<EventSubscribersRegistry>();
                return new EventPublisher(registry, ctxt);

            });

            return p;
        }
    }
}
