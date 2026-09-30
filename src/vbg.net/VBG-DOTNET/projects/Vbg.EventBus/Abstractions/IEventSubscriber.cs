using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Vbg.EventBus.Abstractions
{
    public interface IEventSubscriber<T> : IEventSubscriberBase where T : IEvent
    {
        void OnEvento(T e);
    }
}
