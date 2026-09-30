using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using ServiceStack.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	public interface ICommandSender
	{
		void Send<T>(T command) where T : Command;
	}


	public interface IEventPublisher
	{
		void Publish<T>(T @event) where T : Event;
	}


	public class EventsBus : ICommandSender, IEventPublisher
	{
		ILog _log = LogManager.GetLogger(typeof(EventsBus));

		private readonly Dictionary<Type, List<Action<Message>>> _routes = new Dictionary<Type, List<Action<Message>>>();

		public void RegisterHandler<T>(Action<T> handler) where T : Message
		{
			_log.DebugFormat("Registrato handler per l'evento/comando {0}", typeof(T));

			List<Action<Message>> handlersList = null;

			if (!_routes.TryGetValue(typeof(T), out handlersList))
			{
				handlersList = new List<Action<Message>>();

				_routes.Add(typeof(T), handlersList);
			}

			// Se l'handler è relativo ad un comando allora verifico che non ci sia un altro handler già collegato
			if (typeof(Command).IsAssignableFrom(typeof(T)) && handlersList.Count >= 1)
			{
				_log.ErrorFormat("Il comando {0} ha già un handler registrato", typeof(T));

				throw new BusConfigurationException("E'già stato definito un handler per il comando " + typeof(T));
			}


			handlersList.Add(DelegateAdjuster.CastArgument<Message, T>(x => handler(x)));
		}

		#region IEventPublisher Members

		public void Publish<T>(T @event) where T : Event
		{
			if(_log.IsDebugEnabled)
				_log.DebugFormat("Inizio pubblicazione dell'evento {0}, dati dell'evento: {1}", typeof(T), TypeSerializer.SerializeToString<T>( @event ));

			List<Action<Message>> handlersList = null;

			if (!_routes.TryGetValue(@event.GetType(), out handlersList)) return;

			foreach (var handler in handlersList)
				handler(@event);

			_log.DebugFormat("Evento {0} pubblicato con successo su {1} handlers", typeof(T), handlersList.Count);
		}

		#endregion

		#region ICommandSender Members

		public void Send<T>(T command) where T : Command
		{
			if (_log.IsDebugEnabled)
				_log.DebugFormat("Inizio pubblicazione del comando {0}, dati del comando: {1}", typeof(T), TypeSerializer.SerializeToString<T>(command));

			List<Action<Message>> handlersList = null;

			if (!_routes.TryGetValue(command.GetType(), out handlersList))
			{
				_log.ErrorFormat("Il comando {0} non ha handlers registrati", typeof(T));
				throw new InvalidOperationException("Nessun handler registrato per il comando " + typeof(T));
			}

			handlersList[0](command);

			_log.DebugFormat("Comando {0} pubblicato con successo", typeof(T));
		}

		#endregion
	}
}
