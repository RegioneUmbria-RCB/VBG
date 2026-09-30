namespace Vbg.EventBus
{
    public abstract class EventBusModule
    {
        // protected IEventPublisher EventPublisher;

        internal void RegisterInternal(EventSubscribersRegistry registry)
        {
            // this.EventPublisher = eventPublisher;

            this.Load(registry);
        }

        public abstract void Load(EventSubscribersRegistry registry);
    }
}
