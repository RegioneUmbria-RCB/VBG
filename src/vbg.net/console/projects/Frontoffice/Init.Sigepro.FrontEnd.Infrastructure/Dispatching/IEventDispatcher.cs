using System;
using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;
namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
	public interface IEventDispatcher
	{
		void DispatchEvents(AggregateRoot model);
	}
}
