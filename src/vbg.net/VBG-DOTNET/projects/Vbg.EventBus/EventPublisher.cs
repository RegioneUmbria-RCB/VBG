using System;
using System.Collections.Generic;
using Vbg.EventBus.Abstractions;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Vbg.EventBus
{
    public class EventSubscribersRegistry
    {
        private readonly Dictionary<Type, List<Type>> _subscribers = new Dictionary<Type, List<Type>>();

        // Vedi https://github.com/dotnet-architecture/eShopOnContainers/blob/dev/src/BuildingBlocks/EventBus/EventBus/Abstractions/IEventBus.cs
        public void Add<TEvent, THandler>()
            where TEvent : IEvent
            where THandler : IEventSubscriber<TEvent>
        {
            var tipoEvento = typeof(TEvent);
            var tipoGestore = typeof(THandler);

            if (!this._subscribers.TryGetValue(tipoEvento, out var subscribersPerEvento))
            {
                subscribersPerEvento = new List<Type>();

                this._subscribers[tipoEvento] = subscribersPerEvento;
            }

            subscribersPerEvento.Add(tipoGestore);
        }

        public void Load(EventBusModule module)
        {
            module.RegisterInternal(this);
        }

        public bool HasSubscribersFor(Type type)
        {
            return this._subscribers.ContainsKey(type);
        }

        public List<Type> GetSubscribersFor(Type type) => this._subscribers[type];
    }

    public class EventPublisher : IEventPublisher
    {

        private readonly EventSubscribersRegistry _registry;
        private readonly IDIServiceProvider _kernel;

        public EventPublisher(EventSubscribersRegistry registry, IDIServiceProvider kernel)
        {
            this._registry = registry;
            this._kernel = kernel;
        }


        public void Publish<T>(T evento) where T : IEvent
        {
            var type = typeof(T);

            if (this._registry.HasSubscribersFor(type))
            {
                this._registry.GetSubscribersFor(type).ForEach(x =>
                {
                    var gestore = this._kernel.GetService(x);

                    var methodInfo = x.GetMethod("OnEvento", new[] { typeof(T) });
                    methodInfo.Invoke(gestore, new object[] { evento });
                });
            }
        }

        //public void Load(EventBusModule module)
        //{
        //    module.RegisterInternal(this);
        //}
    }
}
