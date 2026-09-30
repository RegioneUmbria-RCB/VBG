// -----------------------------------------------------------------------
// <copyright file="OperatoriRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneOperatori
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.Data;
using PersonalLib2.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class OperatoriRepository : IOperatoriRepository
	{
		DataBase _db;
		string _alias;

		public OperatoriRepository(DataBase db, string alias)
		{
			this._db = db;
			this._alias = alias;
		}

		public Responsabili GetById(int id)
		{
			return new ResponsabiliMgr(this._db).GetById(this._alias, id);
		}
	}
}
