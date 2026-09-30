// -----------------------------------------------------------------------
// <copyright file="IEventStore.cs" company="">
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
	public interface IEventStream
	{
		/// <summary>
		/// Salva un evento nel mezzo di persistenza
		/// </summary>
		/// <param name="aggregateId">Identificativo univoco del movimento</param>
		/// <param name="event">Evento da persistere</param>
		void Add(int aggregateId, Event @events);


		/// <summary>
		/// Ottiene il flusso degli eventi avvenuti nel movimento identificato dell'id specificato
		/// </summary>
		/// <param name="aggregateId">Identificativo univoco del movimento</param>
		/// <returns>Flusso degli eventi del movimento</returns>
		IEnumerable<Event> GetEventsForAggregate(int aggregateId);
	}
}
