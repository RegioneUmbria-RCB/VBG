using PersonalLib2.Data.Providers;
using PersonalLib2.Exceptions;
using System;
using System.Data;
using System.Reflection;

namespace PersonalLib2.Data.V2.Legacy
{
    [Obsolete]
    public class DataProviderFactory : IDataProviderFactory
    {
        #region private variables

        private Type _connectionType;
        private Type _commandType;
        private Type _dataAdapterType;
        private Type _dataParameterType;
        private ProviderType _provider;
        private readonly IDbConnection _connection = null;

        #endregion

        #region ctors

        [Obsolete]
        public DataProviderFactory(ProviderType provider)
        {
            this.Provider = provider;
        }

        [Obsolete]
        public DataProviderFactory(IDbConnection connection)
        {
            this._connection = connection;
            this.Provider = this.ExtractProviderType(connection);
        }

        #endregion

        #region Provider property

        [Obsolete]
        public ProviderType Provider
        {
            get { return this._provider; }
            set
            {
                string assemblyPartialName = "";
                string connectionName = "";
                string commandName = "";
                string dataAdapterName = "";
                string dataParameterName = "";

                this._provider = value;
                switch (this._provider)
                {
                    case ProviderType.OleDb:
                        {
                            throw new NotImplementedException("Tipo di provider non supportato");
                            //assemblyPartialName = "System.Data";
                            //connectionName = "System.Data.OleDb.OleDbConnection";
                            //commandName = "System.Data.OleDb.OleDbCommand";
                            //dataAdapterName = "System.Data.OleDb.OleDbDataAdapter";
                            //dataParameterName = "System.Data.OleDb.OleDbParameter";
                            //if (_connection != null)
                            //{
                            //    _specifics = new OleDbProvider(((OleDbConnection)_connection).Provider);
                            //}
                            //else
                            //{
                            //    _specifics = null;
                            //}
                            //break;
                        }

                    case ProviderType.SqlClient:
                        {
                            assemblyPartialName = "System.Data";
                            connectionName = "System.Data.SqlClient.SqlConnection";
                            commandName = "System.Data.SqlClient.SqlCommand";
                            dataAdapterName = "System.Data.SqlClient.SqlDataAdapter";
                            dataParameterName = "System.Data.SqlClient.SqlParameter";
                            this.Specifics = new SqlServerProvider();
                            break;
                        }

                    case ProviderType.OracleClient:
                        {
                            assemblyPartialName = "System.Data.OracleClient";
                            connectionName = "System.Data.OracleClient.OracleConnection";
                            commandName = "System.Data.OracleClient.OracleCommand";
                            dataAdapterName = "System.Data.OracleClient.OracleDataAdapter";
                            dataParameterName = "System.Data.OracleClient.OracleParameter";
                            // this._specifics = new OracleProvider();
                            throw new NotSupportedException("Per utilizzare il provider per Oracle è necessario referenziare la libreria PersonalLib2.Framework.Oracle.dll");
                        }

                    case ProviderType.MySqlClient:
                        {
                            assemblyPartialName = "MySqlConnector";
                            connectionName = "MySqlConnector.MySqlConnection";
                            commandName = "MySqlConnector.MySqlCommand";
                            dataAdapterName = "MySqlConnector.MySqlDataAdapter";
                            dataParameterName = "MySqlConnector.MySqlParameter";
                            this.Specifics = new MySqlProvider();
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
                            this.Specifics = new PostGreSQLProvider();
                            break;
                        }
                }

                Assembly objAssembly = Assembly.LoadWithPartialName(assemblyPartialName);
                this._connectionType = objAssembly.GetType(connectionName);
                this._commandType = objAssembly.GetType(commandName);
                this._dataAdapterType = objAssembly.GetType(dataAdapterName);
                this._dataParameterType = objAssembly.GetType(dataParameterName);
            }
        }

        /// <summary>
        /// Ottiene le caratteristiche specifiche del provider.
        /// Es. il nome dei parametri o alcune funzioni come UPPER per oracle.
        /// </summary>
        public IProvider Specifics { get; private set; } = null;

        #endregion

        #region IDbConnection methods

        public IDbConnection CreateConnection()
        {
            IDbConnection conn = null;

            try
            {
                conn = (IDbConnection)Activator.CreateInstance(this._connectionType);
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
            object[] args = { connectionString };

            try
            {
                conn = (IDbConnection)Activator.CreateInstance(this._connectionType, args);
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
                cmd = (IDbCommand)Activator.CreateInstance(this._commandType);
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
            object[] args = { cmdText };

            try
            {
                cmd = (IDbCommand)Activator.CreateInstance(this._commandType, args);
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
            object[] args = { cmdText, connection };

            try
            {
                cmd = (IDbCommand)Activator.CreateInstance(this._commandType, args);
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
            object[] args = { cmdText, connection, transaction };

            try
            {
                cmd = (IDbCommand)Activator.CreateInstance(this._commandType, args);
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
                da = (IDbDataAdapter)Activator.CreateInstance(this._dataAdapterType);
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
            object[] args = { selectCommand };

            try
            {
                da = (IDbDataAdapter)Activator.CreateInstance(this._dataAdapterType, args);
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
            object[] args = { selectCommandText, selectConnection };

            try
            {
                da = (IDbDataAdapter)Activator.CreateInstance(this._dataAdapterType, args);
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
            object[] args = { selectCommandText, selectConnectionString };

            try
            {
                da = (IDbDataAdapter)Activator.CreateInstance(this._dataAdapterType, args);
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
                param = (IDbDataParameter)Activator.CreateInstance(this._dataParameterType);
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
            object[] args = { parameterName, value };

            try
            {
                param = (IDbDataParameter)Activator.CreateInstance(this._dataParameterType, args);
            }
            catch (TargetInvocationException e)
            {
                throw new SystemException(e.InnerException.Message, e.InnerException);
            }

            return param;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType)
        {
            IDbDataParameter param = this.CreateDataParameter();

            if (param != null)
            {
                param.ParameterName = parameterName;
                param.DbType = dataType;
            }

            return param;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size)
        {
            IDbDataParameter param = this.CreateDataParameter();

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
            IDbDataParameter param = this.CreateDataParameter();

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
                case "MySqlConnector.MySqlConnection":
                    return ProviderType.MySqlClient;
                case "Npgsql.NpgsqlConnection":
                    return ProviderType.PostGreSQLClient;

                default:
                    throw (new ProviderException("Invalid Provider." + connection.GetType().ToString()));
            }
        }
    }
}
