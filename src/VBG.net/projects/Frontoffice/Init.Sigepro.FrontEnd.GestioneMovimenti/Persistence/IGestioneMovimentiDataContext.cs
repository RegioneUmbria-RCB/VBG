// -----------------------------------------------------------------------
// <copyright file="GestioneMovimentiDataStore.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence
{
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;

	/// <summary>
	/// Contesto di persistenza dei dati del movimento di frontoffice
	/// </summary>
	public interface IGestioneMovimentiDataContext
	{
		/// <summary>
		/// Restituisce il data store in cui sono memorizzati i dati dei movimenti ed i relativi eventi
		/// </summary>
		/// <returns>Data store del movimento</returns>
		GestioneMovimentiDataStore GetDataStore();
	}
}
