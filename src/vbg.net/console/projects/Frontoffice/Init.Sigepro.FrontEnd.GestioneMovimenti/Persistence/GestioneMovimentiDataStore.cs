// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiDataStore.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;

	/// <summary>
	/// Rappresenta il Data Store utilizzato per far persistere i dati della gestione movimenti
	/// </summary>
	public class GestioneMovimentiDataStore
	{
		public List<JsonEventStream.EventsStreamItem> EventsStream { get; set; }
		public MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

		public GestioneMovimentiDataStore()
		{
			EventsStream = new List<JsonEventStream.EventsStreamItem>();
		}
	}
}
