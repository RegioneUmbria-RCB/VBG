using System;
using System.Data;
using System.Data.OleDb;
using System.Reflection;
using PersonalLib2.Data.Providers;
using PersonalLib2.Exceptions;

namespace PersonalLib2.Data
{
	/// <summary>
	/// The collection of ADO.NET data providers that are supported by <see cref="DataProviderFactory"/>.
	/// </summary>
	public enum ProviderType
	{
		/// <summary>
		/// The OLE DB (<see cref="System.Data.OleDb"/>) .NET data provider.
		/// </summary>
		OleDb = 0,
		/// <summary>
		/// The SQL Server (<see cref="System.Data.SqlClient"/>) .NET data provider.
		/// </summary>
		SqlClient,
		/// <summary>
		/// The Oracle (<see cref="System.Data.OracleClient"/>) .NET data provider.
		/// </summary>
		OracleClient,
		/// <summary>
		/// The Pervasive (<see cref="Pervasive.Data.SqlClient"/>) .NET data provider.
		/// </summary>
		PervasiveClient,
		/// <summary>
		/// The MySql (<see cref="MySql.Data.MySqlClient"/>) .NET data provider.
		/// </summary>
		MySqlClient,
		/// <summary>
		/// The PostGreSQL (<see cref="Npgsql"/>) .NET data provider.
		/// </summary>
		PostGreSQLClient
	} ;

	/// <summary>
	/// The <b>ProviderFactory</b> class abstracts ADO.NET relational data providers through creator methods which return
	/// the underlying <see cref="System.Data"/> interface.
	/// </summary>
	/// <remarks>
	/// This code was inspired by "Design an Effective Data-Access Architecture" by Dan Fox (.netmagazine, vol. 2, no. 7)
	/// </remarks>
	public class DataProviderFactory
	{
		#region private variables

		private Type _connectionType;
		private Type _commandType;
		private Type _dataAdapterType;
		private Type _dataParameterType;
		private ProviderType _provider;
		private IProvider _specifics = null;
		private IDbConnection _connection = null;

		#endregion

		#region ctors

		public DataProviderFactory(ProviderType provider)
		{
			Provider = provider;
		}

		public DataProviderFactory(IDbConnection connection)
		{
			_connection = connection;
			Provider = ExtractProviderType(connection);
		}

		#endregion

		#region Provider property

		internal ProviderType Provider
		{
			get { return _provider; }
			set
			{
				string assemblyPartialName = "";
				string connectionName = "";
				string commandName = "";
				string dataAdapterName = "";
				string dataParameterName = "";

				_provider = value;
				switch (_provider)
				{
					case ProviderType.OleDb:
						{
							assemblyPartialName = "System.Data";
							connectionName = "System.Data.OleDb.OleDbConnection";
							commandName = "System.Data.OleDb.OleDbCommand";
							dataAdapterName = "System.Data.OleDb.OleDbDataAdapter";
							dataParameterName = "System.Data.OleDb.OleDbParameter";
							if (_connection != null)
							{
								_specifics = new OleDbProvider(((OleDbConnection) _connection).Provider);
							}
							else
							{
								_specifics = null;
							}
							break;
						}

					case ProviderType.SqlClient:
						{
							assemblyPartialName = "System.Data";
							connectionName = "System.Data.SqlClient.SqlConnection";
							commandName = "System.Data.SqlClient.SqlCommand";
							dataAdapterName = "System.Data.SqlClient.SqlDataAdapter";
							dataParameterName = "System.Data.SqlClient.SqlParameter";
							_specifics = new SqlServerProvider();
							break;
						}

					case ProviderType.OracleClient:
						{
							assemblyPartialName = "System.Data.OracleClient";
							connectionName = "System.Data.OracleClient.OracleConnection";
							commandName = "System.Data.OracleClient.OracleCommand";
							dataAdapterName = "System.Data.OracleClient.OracleDataAdapter";
							dataParameterName = "System.Data.OracleClient.OracleParameter";
							_specifics = new OracleProvider();
							break;
						}

					case ProviderType.PervasiveClient:
						{
							assemblyPartialName = "Pervasive.Data";
							connectionName = "Pervasive.Data.SqlClient.PsqlConnection";
							commandName = "Pervasive.Data.SqlClient.PsqlCommand";
							dataAdapterName = "Pervasive.Data.SqlClient.PsqlDataAdapter";
							dataParameterName = "Pervasive.Data.SqlClient.PsqlParameter";
							_specifics = new PervasiveProvider();
							break;
						}

					case ProviderType.MySqlClient:
					{
						assemblyPartialName = "MySql.Data";
						connectionName = "MySql.Data.MySqlClient.MySqlConnection";
						commandName = "MySql.Data.MySqlClient.MySqlCommand";
						dataAdapterName = "MySql.Data.MySqlClient.MySqlDataAdapter";
						dataParameterName = "MySql.Data.MySqlClient.MySqlParameter";
						_specifics = new MySqlProvider();
						break;
					}

					case ProviderType.PostGreSQLClient:
					{
//						assembyName = "CoreLab.PostgreSql";
//						connectionName = "CoreLab.PostgreSql.PgSqlConnection";
//						commandName = "CoreLab.PostgreSql.PgSqlCommand";
//						dataAdapterName = "CoreLab.PostgreSql.PgSqlDataAdapter";
//						dataParameterName = "CoreLab.PostgreSql.PgSqlParameter";
//						_specifics = new PostGreSQLProvider();
//						break;

						assemblyPartialName = "Npgsql";
						connectionName = "Npgsql.NpgsqlConnection";
						commandName = "Npgsql.NpgsqlCommand";
						dataAdapterName = "Npgsql.NpgsqlDataAdapter";
						dataParameterName = "Npgsql.NpgsqlParameter";
						_specifics = new PostGreSQLProvider();
						break;
					}
				}

				Assembly objAssembly = Assembly.LoadWithPartialName(assemblyPartialName);
				_connectionType = objAssembly.GetType(connectionName);
				_commandType = objAssembly.GetType(commandName);
				_dataAdapterType = objAssembly.GetType(dataAdapterName);
				_dataParameterType = objAssembly.GetType(dataParameterName);
			}
		}

		/// <summary>
		/// Ottiene le caratteristiche specifiche del provider.
		/// Es. il nome dei parametri o alcune funzioni come UPPER per oracle.
		/// </summary>
		public IProvider Specifics
		{
			get { return _specifics; }
		}

		#endregion

		#region IDbConnection methods

		public IDbConnection CreateConnection()
		{
			IDbConnection conn = null;

			try
			{
				conn = (IDbConnection) Activator.CreateInstance(_connectionType);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return conn;
		}

		public IDbConnection CreateConnection(string connectionString)
		{
			IDbConnection conn = null;
			object[] args = {connectionString};

			try
			{
				conn = (IDbConnection) Activator.CreateInstance(_connectionType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return conn;
		}

		#endregion

		#region IDbCommand methods

		public IDbCommand CreateCommand()
		{
			IDbCommand cmd = null;

			try
			{
				cmd = (IDbCommand) Activator.CreateInstance(_commandType);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return cmd;
		}

		public IDbCommand CreateCommand(string cmdText)
		{
			IDbCommand cmd = null;
			object[] args = {cmdText};

			try
			{
				cmd = (IDbCommand) Activator.CreateInstance(_commandType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return cmd;
		}

		public IDbCommand CreateCommand(string cmdText, IDbConnection connection)
		{
			IDbCommand cmd = null;
			object[] args = {cmdText, connection};

			try
			{
				cmd = (IDbCommand) Activator.CreateInstance(_commandType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return cmd;
		}

		public IDbCommand CreateCommand(string cmdText, IDbConnection connection, IDbTransaction transaction)
		{
			IDbCommand cmd = null;
			object[] args = {cmdText, connection, transaction};

			try
			{
				cmd = (IDbCommand) Activator.CreateInstance(_commandType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return cmd;
		}

		#endregion

		#region IDbDataAdapter methods

		public IDbDataAdapter CreateDataAdapter()
		{
			IDbDataAdapter da = null;

			try
			{
				da = (IDbDataAdapter) Activator.CreateInstance(_dataAdapterType);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return da;
		}

		public IDbDataAdapter CreateDataAdapter(IDbCommand selectCommand)
		{
			IDbDataAdapter da = null;
			object[] args = {selectCommand};

			try
			{
				da = (IDbDataAdapter) Activator.CreateInstance(_dataAdapterType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return da;
		}

		public IDbDataAdapter CreateDataAdapter(string selectCommandText, IDbConnection selectConnection)
		{
			IDbDataAdapter da = null;
			object[] args = {selectCommandText, selectConnection};

			try
			{
				da = (IDbDataAdapter) Activator.CreateInstance(_dataAdapterType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return da;
		}

		public IDbDataAdapter CreateDataAdapter(string selectCommandText, string selectConnectionString)
		{
			IDbDataAdapter da = null;
			object[] args = {selectCommandText, selectConnectionString};

			try
			{
				da = (IDbDataAdapter) Activator.CreateInstance(_dataAdapterType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return da;
		}

		#endregion

		#region IDbDataParameter methods

		public IDbDataParameter CreateDataParameter()
		{
			IDbDataParameter param = null;

			try
			{
				param = (IDbDataParameter) Activator.CreateInstance(_dataParameterType);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return param;
		}

		public IDbDataParameter CreateDataParameter(string parameterName, object value)
		{
			IDbDataParameter param = null;
			object[] args = {parameterName, value};

			try
			{
				param = (IDbDataParameter) Activator.CreateInstance(_dataParameterType, args);
			}
			catch (TargetInvocationException e)
			{
				throw new SystemException(e.InnerException.Message, e.InnerException);
			}

			return param;
		}

		public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType)
		{
			IDbDataParameter param = CreateDataParameter();

			if (param != null)
			{
				param.ParameterName = parameterName;
				param.DbType = dataType;
			}

			return param;
		}

		public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size)
		{
			IDbDataParameter param = CreateDataParameter();

			if (param != null)
			{
				param.ParameterName = parameterName;
				param.DbType = dataType;
				param.Size = size;
			}

			return param;
		}

		public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size, string sourceColumn)
		{
			IDbDataParameter param = CreateDataParameter();

			if (param != null)
			{
				param.ParameterName = parameterName;
				param.DbType = dataType;
				param.Size = size;
				param.SourceColumn = sourceColumn;
			}

			return param;
		}

		#endregion

		/// <summary>
		/// Estrae un il tipo di provider da una connessione.
		/// </summary>
		/// <param name="connection">La connessione da utilizzare.</param>
		/// <returns>Il tipo di provider</returns>
		private ProviderType ExtractProviderType(IDbConnection connection)
		{
			switch (connection.GetType().ToString())
			{
				case "System.Data.SqlClient.SqlConnection":
					return ProviderType.SqlClient;
				case "System.Data.OracleClient.OracleConnection":
					return ProviderType.OracleClient;
				case "System.Data.OleDb.OleDbConnection":
					return ProviderType.OleDb;
				case "Pervasive.Data.SqlClient.PsqlConnection":
					return ProviderType.OleDb;
				case "MySql.Data.MySqlClient.MySqlConnection":
					return ProviderType.MySqlClient;
//				case "CoreLab.PostgreSql.PgSqlConnection":
//					return ProviderType.PostGreSQLClient;
				case "Npgsql.NpgsqlConnection":
					return ProviderType.PostGreSQLClient;

				default:
					throw(new ProviderException("Invalid Provider." + connection.GetType().ToString()));
			}
		}
	}
}