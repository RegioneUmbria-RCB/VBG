// -----------------------------------------------------------------------
// <copyright file="TestEventStore.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TestEventStream : IEventStream
	{
		/*
		private readonly IEventPublisher _publisher;

        private struct EventDescriptor
        {
            
            public readonly Event EventData;
            public readonly int Id;

            public EventDescriptor(int id, Event eventData)
            {
                EventData = eventData;
                Id = id;
            }
        }

		public TestEventStream(IEventPublisher publisher)
        {
            _publisher = publisher;
        }

		private readonly Dictionary<int, List<EventDescriptor>> _current = new Dictionary<int, List<EventDescriptor>>(); 
        
        public void Save(int aggregateId, IEnumerable<Event> events)
        {
            List<EventDescriptor> eventDescriptors;
            if(!_current.TryGetValue(aggregateId, out eventDescriptors))
            {
                eventDescriptors = new List<EventDescriptor>();
                _current.Add(aggregateId,eventDescriptors);
            }

            foreach (var @event in events)
            {
                eventDescriptors.Add(new EventDescriptor(aggregateId,@event));
                _publisher.Publish(@event);
            }
        }

		public IEnumerable<Event> GetEventsForAggregate(int aggregateId)
        {
            List<EventDescriptor> eventDescriptors;
            if (!_current.TryGetValue(aggregateId, out eventDescriptors))
            {
				return new List<Event>();
            }
            return eventDescriptors.Select(desc => desc.EventData);
        }

		public IEnumerable<Event> GetAllEvents()
		{
			List<Event> events = new List<Event>();

			foreach (var key in this._current.Keys)
				events.AddRange(this._current[key].Select( x => x.EventData ));

			return events;
		}

		#region IEventStream Members



		#endregion
		*/
		private struct EventDescriptor
		{

			public readonly Event EventData;
			public readonly int Id;

			public EventDescriptor(int id, Event eventData)
			{
				EventData = eventData;
				Id = id;
			}
		}


		private readonly Dictionary<int, List<EventDescriptor>> _current = new Dictionary<int, List<EventDescriptor>>();


		#region IEventStream Members

		public void Add(int aggregateId, Event @event)
		{
			List<EventDescriptor> eventDescriptors;

			if (!_current.TryGetValue(aggregateId, out eventDescriptors))
			{
				eventDescriptors = new List<EventDescriptor>();
				_current.Add(aggregateId, eventDescriptors);
			}

			eventDescriptors.Add( new EventDescriptor( aggregateId , @event ) );
		}

		public IEnumerable<Event> GetEventsForAggregate(int aggregateId)
		{
			List<EventDescriptor> eventDescriptors;
			if (!_current.TryGetValue(aggregateId, out eventDescriptors))
			{
				return new List<Event>();
			}
			return eventDescriptors.Where( x => x.Id == aggregateId).Select(desc => desc.EventData);
		}

		#endregion

		public IEnumerable<Event> GetAllEvents()
		{
			List<Event> events = new List<Event>();

			foreach (var key in this._current.Keys)
				events.AddRange(this._current[key].Select(x => x.EventData));

			return events;
		}
	}
}
