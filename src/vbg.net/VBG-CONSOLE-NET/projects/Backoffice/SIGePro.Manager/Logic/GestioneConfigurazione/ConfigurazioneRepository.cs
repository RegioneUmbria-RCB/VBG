// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneRepository.cs" company="">
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
	public class ConfigurazioneRepository : IConfigurazioneRepository
	{
		DataBase _db;
		string _alias;

		public ConfigurazioneRepository(DataBase db, string alias)
		{
			this._db = db;
			this._alias = alias;
		}

		public Configurazione GetbySoftware(string software)
		{
			return new ConfigurazioneMgr(this._db).GetByIdComuneESoftwareSovrascrivendoTT(this._alias, software);
		}
	}
}
