// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneAreaRiservataRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneConfigurazione
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
	public class ConfigurazioneAreaRiservataRepository : IConfigurazioneAreaRiservataRepository
	{
		DataBase _db;
		string _alias;

		public ConfigurazioneAreaRiservataRepository(DataBase db, string alias)
		{
			this._db = db;
			this._alias = alias;
		}

		public FoArConfigurazione GetBySoftware(string software)
		{
			return new FoArConfigurazioneMgr(this._db).LeggiDati(this._alias, software);
		}
	}
}
