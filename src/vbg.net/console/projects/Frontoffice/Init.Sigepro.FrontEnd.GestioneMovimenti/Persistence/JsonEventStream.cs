// -----------------------------------------------------------------------
// <copyright file="JsonEventStream.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
	using CuttingEdge.Conditions;
	using ServiceStack.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class JsonEventStream : IEventStream
	{
		public class EventsStreamItem
		{
			public int AggregateRootId { get; set; }
			public string EventData { get; set; }
			public string EventType { get; set; }
		}

		IGestioneMovimentiDataContext _dataContext;
		EventJsonSerializer _jsonSerializer;

		public JsonEventStream(IEventTypesRegistry eventTypesRegistry, IGestioneMovimentiDataContext dataContext)
		{
			Condition.Requires(eventTypesRegistry, "eventTypesRegistry").IsNotNull();
			Condition.Requires(dataContext, "dataContext").IsNotNull();

			this._jsonSerializer = new EventJsonSerializer( eventTypesRegistry );
			this._dataContext = dataContext;
		}


		#region IEventStream Members

		public void Add(int aggregateId, Event @event)
		{
			var typeName = @event.GetType().FullName;
			var eventData = this._jsonSerializer.Serialize( @event );

			this._dataContext.GetDataStore( ).EventsStream.Add(new EventsStreamItem
			{
				AggregateRootId = aggregateId,
				EventType = typeName,
				EventData = eventData
			});
		}

		public IEnumerable<Event> GetEventsForAggregate(int aggregateId)
		{
			return this._dataContext.GetDataStore().EventsStream
						.Where(x => x.AggregateRootId == aggregateId)
						.Select(@event => (Event)this._jsonSerializer.Deserialize( @event.EventType , @event.EventData ));
		}

		#endregion
	}
}
