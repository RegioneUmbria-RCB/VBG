// -----------------------------------------------------------------------
// <copyright file="RepositoryBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Repositories
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;
	using System.Reflection;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using log4net;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RepositoryBase<T>: IRepository<T> where T:AggregateRoot
	{
		IEventStream _eventStream;
		IEventPublisher _publisher;
		ILog _log = LogManager.GetLogger(typeof(RepositoryBase<T>));

		public RepositoryBase(IEventPublisher publisher,IEventStream eventStream)
		{
			this._eventStream = eventStream;
			this._publisher = publisher;
		}

		#region IRepository<T> Members

		public T GetById(int aggregateId)
		{
			_log.DebugFormat("Lettura degli eventi per l'aggregate root con id {0}", aggregateId);

			var events = this._eventStream.GetEventsForAggregate( aggregateId );

			_log.DebugFormat("{0} eventi trovati", events.Count());

			if (events.Count() == 0)
				return null;

			var ctor = typeof(T).GetConstructors(BindingFlags.Instance | BindingFlags.NonPublic)[0];

			return (T)ctor.Invoke(new object[] { events });
		}

		public void Save(T aggregateRoot)
		{
			_log.DebugFormat("Inizio salvataggio degli eventi dell'AR con id {0}", aggregateRoot.Id);

			var aggregateId = aggregateRoot.Id;
			var eventiInSospeso = aggregateRoot.GetEventiInSospeso();

			_log.DebugFormat("Trovati {0} eventi in sospeso, inizio salvataggio nello stream", eventiInSospeso.Count());
			
			foreach (var evento in eventiInSospeso)
				this._eventStream.Add(aggregateId, evento);

			_log.Debug("Commit degli eventi in sospeso");

			aggregateRoot.CommitEventiInSospeso();

			_log.Debug("Inizio pubblicazione degli eventi salvati");

			foreach (var evento in eventiInSospeso)
				this._publisher.Publish( evento );

			_log.Debug("Pubblicazione degli eventi salvati completata con successo");
		}

		#endregion
	}
}
