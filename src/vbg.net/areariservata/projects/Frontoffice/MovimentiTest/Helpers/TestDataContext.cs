// -----------------------------------------------------------------------
// <copyright file="TestDataContext.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.Helpers
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TestDataContext : IGestioneMovimentiDataContext
	{
		private readonly GestioneMovimentiDataStore _dataStore = new GestioneMovimentiDataStore();

		#region IGestioneMovimentiDataContext Members

		public GestioneMovimentiDataStore GetDataStore()
		{
			return this._dataStore;
		}

		#endregion
	}
}
