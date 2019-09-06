namespace PersonalLib2.Data.Providers
{
	/// <summary>
	/// Descrizione di riepilogo per PostGreSQLProvider.
	/// </summary>
	internal class PostGreSQLProvider : IProvider
	{
		#region IProvider

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string QueryParameterName(string columnName)
		{
			return ":" + columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string ParameterName(string columnName)
		{
			return ":" + columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string UCaseFunction(string columnName)
		{
			return "UPPER(" + columnName + ")";
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string MaxFunction(string columnName)
		{
			return "MAX(" + columnName + ")";
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName"></param>
		/// <param name="value"></param>
		/// <returns></returns>
		public string NvlFunction(string columnName, object value)
		{
			if (value.GetType() == typeof(System.String))
			{
				return "COALESCE(" + columnName + ",'" + value + "')";
			}
			else
			{
				return "COALESCE(" + columnName + "," + value + ")";
			}
		}

		/// <summary>
		/// Utilizzato per ritornare la sintassi della funzione che permette di 
		/// estrarre una sottostringa.
		/// Es. in oracle equivale a SUBSTR(stringa,start,length)
		/// </summary>
		/// <param name="stringValue">Stringa o colonna dalla quale estrarre la sottostringa. 
		/// Se si tratta di stringa vanno indicati anche i caratteri terminatori della stringa. Es. 'pippo'.</param>
		/// <param name="start">Posizione iniziale.</param>
		/// <param name="length">Numero di caratteri da estrarre.</param>
		/// <returns>
		/// Es. 
		///		Oracle=		SUBSTR('testo',2,3) --> est
		///		Sql Server=	SUBSTRING('testo',2,3) --> est
		/// </returns>
		public string SubstrFunction(string stringValue, int start, int length)
		{
			//if (!stringValue.Trim().StartsWith("'"))
			//{
			//    stringValue = "'" + stringValue.Replace("'", "''") + "'";
			//}
			return "SUBSTR(" + stringValue + "," + start + "," + length + ")";
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

		/// <summary>
		/// Utilizzato per effettuare una somma tra colonne
		/// </summary>
		/// <param name="columnName">Nome della colonna</param>
		/// <returns></returns>
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
			if(confrontoUCase)
				columnName = UCaseFunction( columnName );
			
			return string.Format(" ({0} like '%' || {1} || '%') ", columnName , QueryParameterName( nomeParametro ));
		}

		/// <summary>
		/// Utilizzato per leggere la lunghezza del valore di una colonna
		/// </summary>
		/// <param name="columnName"></param>
		/// <returns></returns>
		public string LengthFunction(string columnName)
		{
			return string.Format(" CHAR_LENGTH({0}) ", columnName);
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <returns></returns>
		public Provider DBMSName()
		{
			return Provider.POSTGRESQL;
		}
		#endregion

	}
}
