// -----------------------------------------------------------------------
// <copyright file="EventHandlerConfigurator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class EventHandlerConfigurator
	{
		List<IEventHandler> _handlers = new List<IEventHandler>();
		//EventsBus _bus = null;

		private EventHandlerConfigurator()
		{

		}

		public static EventHandlerConfigurator Configure()
		{
			return new EventHandlerConfigurator();
		}

		public EventHandlerConfigurator WithHandler(IEventHandler handler)
		{
			this._handlers.Add(handler);

			return this;
		}

		public EventHandlerConfigurator WithHandlers(IEnumerable<IEventHandler> handlers)
		{
			this._handlers.AddRange(handlers);

			return this;
		}

		public void OnBus(EventsBus bus)
		{
			foreach (var handler in this._handlers)
			{
				var interfs = handler.GetType()
									 .GetInterfaces()
									 .Where(x =>
										x.IsGenericType &&
										x.GetGenericTypeDefinition() == typeof(Handles<>))
									 .Select(x => new
									 {
										 TipoArgomento = x.GetGenericArguments()[0],
										 Metodo = handler.GetType().GetMethod("Handle", new Type[] { x.GetGenericArguments()[0] })
									 });

				foreach (var interf in interfs)
				{
					var genericMethod = bus.GetType().GetMethod("RegisterHandler").MakeGenericMethod(interf.TipoArgomento);
					var action = typeof(Action<>).MakeGenericType(interf.TipoArgomento);
					var del = Delegate.CreateDelegate(action, handler, interf.Metodo);

					genericMethod.Invoke(bus, new object[] { del });
				}
			}
		}


	}
}
