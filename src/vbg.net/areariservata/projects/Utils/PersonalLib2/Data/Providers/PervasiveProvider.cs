using System;
namespace PersonalLib2.Data.Providers
{
	/// <summary>
	/// Descrizione di riepilogo per OracleProvider.
	/// </summary>
	internal class PervasiveProvider : IProvider
	{
		#region IProvider

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string QueryParameterName(string columnName)
		{
			return "?" + columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string ParameterName(string columnName)
		{
			return "?" + columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string UCaseFunction(string columnName)
		{
			return columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string MaxFunction(string columnName)
		{
			return columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName"></param>
		/// <param name="value"></param>
		/// <returns></returns>
		public string NvlFunction(string columnName, object value)
		{
			return columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="stringValue"></param>
		/// <param name="start"></param>
		/// <param name="length"></param>
		/// <returns></returns>
		public string SubstrFunction(string stringValue, int start, int length)
		{
			if (!stringValue.Trim().StartsWith("'"))
			{
				stringValue = "'" + stringValue.Replace("'", "''") + "'";
			}
			return "SUBSTRING(" + stringValue + "," + start + "," + length + ")";
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName"></param>
		/// <returns></returns>
		public string RTrimFunction(string columnName)
		{
			return "RTRIM(" + columnName + ")";
		}

		public string ToCharFunction(string columnName)
		{
			return columnName;
		}

		public string ToIntegerFunction(string columnName)
		{
			return columnName;
		}

		public string SumFunction(string columnName)
		{
			return string.Format("SUM({0})", columnName);
		}

		/// <summary>
		/// Effettua una like in un campo clob
		/// </summary>
		/// <param name="columnName">Nome della colonna su cui effettuare il confronto like</param>
		/// <param name="valoreDaCercare">nome del parametro che conterrà il valore con cui fare il confronto. il parametro viene già convertito nel formato del db</param>
		/// <param name="confrontoUCase">true se occorre utilizzare un confronto su testo tutto maiuscolo</param>
		/// <returns></returns>
		public string ClobLike(string columnName, string nomeParametro, bool confrontoUCase)
		{
			throw new NotImplementedException();
		}

		/// <summary>
		/// Utilizzato per leggere la lunghezza del valore di una colonna
		/// </summary>
		/// <param name="columnName"></param>
		/// <returns></returns>
		public string LengthFunction(string columnName)
		{
			throw new NotImplementedException();
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <returns></returns>
		public Provider DBMSName()
		{
			return Provider.PERVASIVE;
		}
		#endregion

	}

}