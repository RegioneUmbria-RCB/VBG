using System;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per LogSuapMgr.
	/// </summary>
	public class LogSuapMgr: BaseManager
	{
		public LogSuapMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB
		public LogSuap Insert( LogSuap p_class )
		{
			Validate(p_class, AmbitoValidazione.Insert);
			db.Insert( p_class );
			return p_class;
		}

		private void Validate(LogSuap p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);
		}
		#endregion
	}
}
