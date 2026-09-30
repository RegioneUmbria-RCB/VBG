using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	public class EventDispatcher : IEventDispatcher
	{
		IEventPublisher _eventsBus;

		public EventDispatcher(IEventPublisher eventsBus)
		{
			_eventsBus = eventsBus;
		}

		public void DispatchEvents(AggregateRoot model)
		{
			var newEvents = model.GetEventiInSospeso();

			foreach(var @event in newEvents)
				_eventsBus.Publish(@event);

			model.CommitEventiInSospeso();
		}


	}
}
