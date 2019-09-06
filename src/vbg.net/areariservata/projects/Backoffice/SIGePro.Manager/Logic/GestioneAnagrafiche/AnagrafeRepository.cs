// -----------------------------------------------------------------------
// <copyright file="AnagrafeRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using PersonalLib2.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class AnagrafeRepository : IAnagrafeRepository
	{
		DataBase _db;
		string _idComune;
		AnagrafeMgr _mgr;

		public AnagrafeRepository(DataBase db, string idComune)
		{
			this._db = db;
			this._idComune = idComune;
			this._mgr = new AnagrafeMgr(db);
		}

		public Data.Anagrafe GetById(int codiceAnagrafe)
		{
			return this._mgr.GetById(this._idComune, codiceAnagrafe);
		}
	}
}
