// -----------------------------------------------------------------------
// <copyright file="OggettiRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneOggetti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using PersonalLib2.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class OggettiRepository :IOggettiRepository
	{
		DataBase _db;
		string _alias; 

		public OggettiRepository(DataBase db, string alias)
		{
			this._db = db;
			this._alias = alias;
		}

		public Data.Oggetti GetById(int id)
		{
			return new OggettiMgr(this._db).GetById(this._alias, id);
		}
	}
}
