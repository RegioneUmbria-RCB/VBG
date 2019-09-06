using PersonalLib2.Exceptions;
using System.Data.OleDb;

namespace PersonalLib2.Data.Providers
{
	/// <summary>
	/// Descrizione di riepilogo per OracleProvider.
	/// </summary>
	internal class OleDbProvider : IProvider
	{
		/// <summary>
		/// Costruttore di OleDbProvider, va specificato il nome del provider OleDb, nella stringa di connessione segue la dicitura Provider=.
		/// </summary>
		/// <param name="OleDbConnectionProviderName">Per il provider ole di oracle la stringa è Provider=OraOleDb.Oracle.1;User Id=...
		///		quindi il parametro OleDbConnectionProvider sarà="OraOleDb.Oracle.1" </param>
		public OleDbProvider(string OleDbConnectionProviderName)
		{
			_oleDbConnectionProviderName = OleDbConnectionProviderName;
		}

		/// <summary>
		/// Costruttore di OleDbProvider per connessione tramite jdbc. Va utilizzata una stringa di connessione Grasshopper per jdbc.
		/// Vedi connectionsReference/JdbcConnectionStrings.txt
		/// </summary>
		/// <param name="oleDbConnection">Connessione che contiene una stringa di connessione per GrassHopper: vedi connectionsReference/JdbcConnectionStrings.txt</param>
		public OleDbProvider(OleDbConnection oleDbConnection)
		{
			_oleDbConnectionProviderName = GetJdbcProvider(oleDbConnection.ConnectionString);
		}

		public OleDbProvider()
		{
		}

		private string _oleDbConnectionProviderName = null;
		private IProvider _provider = null;

		#region IProvider

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string QueryParameterName(string columnName)
		{
			return "?";
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string ParameterName(string columnName)
		{
			return columnName;
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string UCaseFunction(string columnName)
		{
			return GetProvider.UCaseFunction(columnName);
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName">Vedi PersonalLib2.Data.Providers.IProvider.</param>
		/// <returns>Vedi PersonalLib2.Data.Providers.IProvider.</returns>
		public string MaxFunction(string columnName)
		{
			return GetProvider.MaxFunction(columnName);
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="columnName"></param>
		/// <param name="value"></param>
		/// <returns></returns>
		public string NvlFunction(string columnName, object value)
		{
			return GetProvider.NvlFunction(columnName, value);
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
			return GetProvider.SubstrFunction(stringValue, start, length);
		}

		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <param name="stringValue"></param>
		/// <returns></returns>
		public string RTrimFunction(string stringValue)
		{
			return GetProvider.RTrimFunction(stringValue);
		}

		public string ToCharFunction(string columnName)
		{
			return GetProvider.ToCharFunction(columnName);
		}

		public string ToIntegerFunction(string columnName)
		{
			return GetProvider.ToIntegerFunction(columnName);
		}

		/// <summary>
		/// Utilizzato per effettuare una somma tra colonne
		/// </summary>
		/// <param name="columnName">Nome della colonna</param>
		/// <returns></returns>
		public string SumFunction(string columnName)
		{
			return GetProvider.SumFunction(columnName);
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
			return GetProvider.ClobLike(columnName,nomeParametro , confrontoUCase);
		}

		/// <summary>
		/// Utilizzato per leggere la lunghezza del valore di una colonna
		/// </summary>
		/// <param name="columnName"></param>
		/// <returns></returns>
		public string LengthFunction(string columnName)
		{
			return GetProvider.LengthFunction(columnName);
		}


		/// <summary>
		/// Vedi PersonalLib2.Data.Providers.IProvider.
		/// </summary>
		/// <returns></returns>
		public Provider DBMSName()
		{
			return GetProvider.DBMSName();
		}




		#endregion

		/// <summary>
		/// Estrae il provider a partire da una connessione del tipo
		/// "JdbcDriverClassName=com.mysql.jdbc.Driver;JdbcURL=jdbc:mysql://dbmysql5:3306/sigeproexport;user=sigeproexport;password=export;"
		/// utilizzata da Grasshopper per istanziare connessioni jdbc per mysql, postgres, oracle
		/// Vedi:
		/// http://dev.mainsoft.com/Default.aspx?tabid=32&src=vmwdoc_Using_a_JDBC_driver_101.htm
		/// PersonalLib2\connectionReference\JdbcConnectionStrings.txt
		/// </summary>
		/// <param name="jdbcConnectionString">E' la stringa di connessione dalla quale estrarre il provider</param>
		/// <returns>Ritorna il nome del provider OleDb con il quale instanziare il provider del tipo corretto.</returns>
		private string GetJdbcProvider(string jdbcConnectionString)
		{
			const string constJDCN = "JdbcDriverClassName"; //Serve a capire se la connessione è un oledb provider utilizzato da Grasshopper per invocare jdbc.
			int initPos = 0;
			int endPos = 0;
			string retProvider = null;
			string providerName = "";

			initPos = jdbcConnectionString.IndexOf(constJDCN) + constJDCN.Length + 1;
			if (initPos != -1)
			{
				endPos = jdbcConnectionString.IndexOf(";");
				if (endPos == -1)
					endPos = jdbcConnectionString.Length;
				providerName = jdbcConnectionString.Substring(initPos, endPos - initPos);
				switch (providerName)
				{
					case "com.mysql.jdbc.Driver":
						retProvider = "OLEMYSQL.MYSQLSOURCE.1";
						break;

					case "org.postgresql.Driver":
						retProvider = "POSTGRESQL OLE DB PROVIDER";
						break;

					case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
						retProvider = "SQLOLEDB.1";
						break;

					case "oracle.jdbc.OracleDriver":
						retProvider = "ORAOLEDB.ORACLE.1";
						break;

					default:
						throw new DatabaseException("Provider jdbc non gestito. JdbcDriverClassName=" + providerName);
						break;
				}
			}

			return retProvider;
		}


		private IProvider GetProvider
		{
			get
			{
				if (_provider != null)
					return _provider;
				switch (_oleDbConnectionProviderName.ToUpper())
				{
					case "ORAOLEDB.ORACLE.1":
					case "MSDAORA.1":
						_provider = new OracleProvider();
						return _provider;
					case "SQLOLEDB.1":
						_provider = new SqlServerProvider();
						return _provider;
					case "MYSQLPROV": //è il provider oledb "myoledb-3.9.6.msi" che non supporta transazioni
					case "OLEMYSQL.MYSQLSOURCE.1":	//è il provider "MySQL Provider.msi" di Cherry Software a pagamento
						_provider = new MySqlProvider();
						return _provider;
					case "POSTGRESQL OLE DB PROVIDER":
						_provider = new PostGreSQLProvider();
						return _provider;
					case null:
						throw new ProviderException("Property OleDbConnectionProvider is null.");
					default:
						throw new ProviderException("Invalid OleDbConnectionProvider value (" + _oleDbConnectionProviderName + ")");
				}
			}
		}
	}

}