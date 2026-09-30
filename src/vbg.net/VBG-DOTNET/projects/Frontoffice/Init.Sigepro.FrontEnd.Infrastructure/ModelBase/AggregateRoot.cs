using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using ReflectionMagic;
using System.Collections.Generic;

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
                this.ApplyChange(@event, false);
        }


        protected void ApplyChange(Event @event)
        {
            this.ApplyChange(@event, true);
        }

        protected void ApplyChange(Event @event, bool isNew)
        {
            this.AsDynamic().Apply(@event);

            if (isNew)
                this._listaEventiInSospeso.Add(@event);
        }

        private List<Event> _listaEventiInSospeso = new List<Event>();

        internal IEnumerable<Event> GetEventiInSospeso()
        {
            return this._listaEventiInSospeso;
        }

        internal void CommitEventiInSospeso()
        {
            this._listaEventiInSospeso = new List<Event>();
        }
    }
}
