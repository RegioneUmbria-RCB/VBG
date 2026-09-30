using MySqlConnector;
using PersonalLib2.Data.Providers;
using System;
using System.Data;

namespace PersonalLib2.Data.V2
{
    public class MySqlDataProviderV2 : IDataProviderFactory
    {
        public IProvider Specifics { get; } = new MySqlProvider();

        public ProviderType Provider => ProviderType.MySqlClient;

        public MySqlDataProviderV2()
        {
        }

        public IDbCommand CreateCommand()
        {
            return new MySqlCommand();
        }

        public IDbCommand CreateCommand(string cmdText)
        {
            return new MySqlCommand(cmdText);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection)
        {
            if (!(connection is MySqlConnection))
            {
                throw new ArgumentException("La connessione passata non è una MySqlConnection");
            }

            return new MySqlCommand(cmdText, (MySqlConnection)connection);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection, IDbTransaction transaction)
        {
            if (!(connection is MySqlConnection))
            {
                throw new ArgumentException("La connessione passata non è una MySqlConnection");
            }

            if (!(transaction is MySqlTransaction))
            {
                throw new ArgumentException("La transazione passata non è una MySqlTransaction");
            }

            return new MySqlCommand(cmdText, (MySqlConnection)connection, (MySqlTransaction)transaction);
        }

        public IDbConnection CreateConnection()
        {
            return new MySqlConnection();
        }

        public IDbConnection CreateConnection(string connectionString)
        {
            return new MySqlConnection(connectionString);
        }

        public IDbDataAdapter CreateDataAdapter()
        {
            return new MySqlDataAdapter();
        }

        public IDbDataAdapter CreateDataAdapter(IDbCommand selectCommand)
        {
            return new MySqlDataAdapter((MySqlCommand)selectCommand);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, IDbConnection selectConnection)
        {
            return new MySqlDataAdapter(selectCommandText, (MySqlConnection)selectConnection);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, string selectConnectionString)
        {
            return new MySqlDataAdapter(selectCommandText, selectConnectionString);
        }

        public IDbDataParameter CreateDataParameter()
        {
            return new MySqlParameter();
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType)
        {
            var par = new MySqlParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size)
        {
            var par = new MySqlParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;
            par.Size = size;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size, string sourceColumn)
        {
            var par = new MySqlParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;
            par.Size = size;
            par.SourceColumn = sourceColumn;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, object value)
        {
            return new MySqlParameter(parameterName, value);
        }
    }
}
