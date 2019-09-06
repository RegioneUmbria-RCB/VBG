// -----------------------------------------------------------------------
// <copyright file="EventJsonSerializer.cs" company="">
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
    using Newtonsoft.Json;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class EventJsonSerializer
	{
		IEventTypesRegistry _registry;

		public EventJsonSerializer(IEventTypesRegistry registry)
		{
			this._registry = registry;
		}

		public string Serialize(Event @event)
		{
            return JsonConvert.SerializeObject(@event, @event.GetType(),null); //TypeSerializer.SerializeToString(@event, @event.GetType());
		}

		public object Deserialize(string typeName, string serializedData)
		{
			var type = this._registry.GetTypeByTypeName(typeName);
            return JsonConvert.DeserializeObject(serializedData, type); //(Event)TypeSerializer.DeserializeFromString(serializedData, type);
		}


	}
}
