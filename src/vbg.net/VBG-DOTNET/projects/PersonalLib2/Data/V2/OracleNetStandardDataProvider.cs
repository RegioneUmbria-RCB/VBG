
#if NET9_0_OR_GREATER
using Oracle.ManagedDataAccess.Client;
using PersonalLib2.Data.Providers;
using System;
using System.Data;

namespace PersonalLib2.Data.V2
{
#pragma warning disable CS0618
    public class OracleNetStandardDataProvider : IDataProviderFactory
    {
        public IProvider Specifics { get; } = new OracleProvider();

        public ProviderType Provider => ProviderType.OracleClient;

        public OracleNetStandardDataProvider()
        {
        }

        public IDbCommand CreateCommand()
        {
            return new OracleCommand();
        }

        public IDbCommand CreateCommand(string cmdText)
        {
            return new OracleCommand(cmdText);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection)
        {
            if (connection is not OracleConnection)
            {
                throw new ArgumentException("La connessione passata non è una OracleConnection");
            }

            return new OracleCommand(cmdText, (OracleConnection)connection);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection, IDbTransaction transaction)
        {
            if (!(connection is OracleConnection))
            {
                throw new ArgumentException("La connessione passata non è una OracleConnection");
            }

            if (!(transaction is OracleTransaction))
            {
                throw new ArgumentException("La transazione passata non è una OracleTransaction");
            }

            var cmd = new OracleCommand(cmdText, (OracleConnection)connection);
            cmd.Transaction = (OracleTransaction)transaction;

            return cmd;
        }

        public IDbConnection CreateConnection()
        {
            return new OracleConnection();
        }

        public IDbConnection CreateConnection(string connectionString)
        {
            return new OracleConnection(connectionString);
        }

        public IDbDataAdapter CreateDataAdapter()
        {
            return new OracleDataAdapter();
        }

        public IDbDataAdapter CreateDataAdapter(IDbCommand selectCommand)
        {
            return new OracleDataAdapter((OracleCommand)selectCommand);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, IDbConnection selectConnection)
        {
            return new OracleDataAdapter(selectCommandText, (OracleConnection)selectConnection);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, string selectConnectionString)
        {
            return new OracleDataAdapter(selectCommandText, selectConnectionString);
        }

        public IDbDataParameter CreateDataParameter()
        {
            return new OracleParameter();
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType)
        {
            var par = new OracleParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size)
        {
            var par = new OracleParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;
            par.Size = size;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size, string sourceColumn)
        {
            var par = new OracleParameter();
            par.ParameterName = parameterName;
            par.DbType = dataType;
            par.Size = size;
            par.SourceColumn = sourceColumn;

            return par;
        }

        public IDbDataParameter CreateDataParameter(string parameterName, object value)
        {
            return new OracleParameter(parameterName, value);
        }

        // Chiamare nell'application.start
        public static void RegisterProvider()
        {
            ManagedDataProviderRegistry.RegisterProvider(ProviderType.OracleClient, () => new OracleNetStandardDataProvider());
        }
    }

#pragma warning restore CS0618
}
#endif