// -----------------------------------------------------------------------
// <copyright file="ITypesRegistry.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using System.Reflection;
	using System.Configuration;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IEventTypesRegistry
	{
		//void RegisterEventsFromAssembly(Assembly assembly);
		Type GetTypeByTypeName(string typeName);
	}

	public class EventTypesRegistry: IEventTypesRegistry
	{
		#region Factory
		public class EventTypesRegistryBuilder
		{
			List<Assembly> _assemblies = new List<Assembly>();

			public EventTypesRegistryBuilder FromAssembly(Assembly asm)
			{
				this._assemblies.Add(asm);

				return this;
			}

			public IEventTypesRegistry Now()
			{
				var registry = new EventTypesRegistry();

				foreach (var asm in this._assemblies)
				{
					registry.RegisterEventsFromAssembly(asm);
				}

				return registry;
			}
		}


		public static EventTypesRegistryBuilder RegisterEvents()
		{
			return new EventTypesRegistryBuilder();
		}

		#endregion

		protected class EventTypeRegistryItem
		{
			public readonly string TypeName;
			public readonly Type Type;
			public readonly Assembly Assembly;

			public EventTypeRegistryItem(string typeName , Type type , Assembly assembly)
			{
				this.TypeName = typeName;
				this.Type = type;
				this.Assembly = assembly;
			}
		}

		Dictionary<string, EventTypeRegistryItem> _registeredTypes = new Dictionary<string, EventTypeRegistryItem>();

		protected EventTypesRegistry()
		{

		}

		protected void RegisterEventsFromAssembly(Assembly assembly)
		{
			var exportedEvents = assembly.GetTypes()
										 .Where(x => typeof(Event).IsAssignableFrom(x))
										 .Select(x => new EventTypeRegistryItem(x.FullName, x, assembly));

			foreach (var evt in exportedEvents)
			{
				if (this._registeredTypes.ContainsKey(evt.TypeName))
				{
					var tipoRegistrato = this._registeredTypes[evt.TypeName];

					throw new Exception(String.Format("Il tipo di evento {0} è già stato registrato dall'assembly {1}", tipoRegistrato.TypeName, tipoRegistrato.Assembly));
				}

				this._registeredTypes.Add(evt.TypeName, evt);
			}
		}

		#region IEventTypesRegistry Members

		

		public Type GetTypeByTypeName(string typeName)
		{
			EventTypeRegistryItem item = null;

			if (!this._registeredTypes.TryGetValue(typeName, out item))
				return null;

			return item.Type;
		}

		#endregion
	}
}
