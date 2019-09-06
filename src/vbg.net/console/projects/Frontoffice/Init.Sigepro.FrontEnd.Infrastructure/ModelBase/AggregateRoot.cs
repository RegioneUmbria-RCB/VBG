using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using ReflectionMagic;

namespace Init.Sigepro.FrontEnd.Infrastructure.ModelBase
{
	public abstract class AggregateRoot
	{
		public abstract int Id { get; }

		protected AggregateRoot()
		{ }

		protected AggregateRoot(IEnumerable<Event> events)
		{
			foreach (var @event in events)
				ApplyChange(@event, false);
		}


		protected void ApplyChange(Event @event)
		{
			ApplyChange(@event, true);
		}

		protected void ApplyChange(Event @event, bool isNew)
		{
			this.AsDynamic().Apply(@event);

			if (isNew)
				this._listaEventiInSispeso.Add(@event);
		}


		List<Event> _listaEventiInSispeso = new List<Event>();

		internal IEnumerable<Event> GetEventiInSospeso()
		{
			return _listaEventiInSispeso;
		}

		internal void CommitEventiInSospeso()
		{
			_listaEventiInSispeso = new List<Event>();
		}
	}
}
