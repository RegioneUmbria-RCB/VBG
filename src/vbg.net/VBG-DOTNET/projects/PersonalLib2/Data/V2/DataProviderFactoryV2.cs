using PersonalLib2.Data.Providers;
using PersonalLib2.Data.V2.Legacy;
using System.Data;

namespace PersonalLib2.Data.V2
{
    public class DataProviderFactoryV2 : IDataProviderFactory
    {
        private readonly IDataProviderFactory _inner;

        public DataProviderFactoryV2(ProviderType provider)
        {
            if (ManagedDataProviderRegistry.Supports(provider))
            {
                this._inner = ManagedDataProviderRegistry.Create(provider);
            }
            else
            {
                this._inner = new DataProviderFactory(provider);
            }
        }

        public IProvider Specifics => this._inner.Specifics;

        public ProviderType Provider => this._inner.Provider;

        public IDbCommand CreateCommand()
        {
            return this._inner.CreateCommand();
        }

        public IDbCommand CreateCommand(string cmdText)
        {
            return this._inner.CreateCommand(cmdText);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection)
        {
            return this._inner.CreateCommand(cmdText, connection);
        }

        public IDbCommand CreateCommand(string cmdText, IDbConnection connection, IDbTransaction transaction)
        {
            return this._inner.CreateCommand(cmdText, connection, transaction);
        }

        public IDbConnection CreateConnection()
        {
            return this._inner.CreateConnection();
        }

        public IDbConnection CreateConnection(string connectionString)
        {
            return this._inner.CreateConnection(connectionString);
        }

        public IDbDataAdapter CreateDataAdapter()
        {
            return this._inner.CreateDataAdapter();
        }

        public IDbDataAdapter CreateDataAdapter(IDbCommand selectCommand)
        {
            return this._inner.CreateDataAdapter(selectCommand);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, IDbConnection selectConnection)
        {
            return this._inner.CreateDataAdapter(selectCommandText, selectConnection);
        }

        public IDbDataAdapter CreateDataAdapter(string selectCommandText, string selectConnectionString)
        {
            return this._inner.CreateDataAdapter(selectCommandText, selectConnectionString);

        }

        public IDbDataParameter CreateDataParameter()
        {
            return this._inner.CreateDataParameter();
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType)
        {
            return this._inner.CreateDataParameter(parameterName, dataType);
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size)
        {
            return this._inner.CreateDataParameter(parameterName, dataType, size);
        }

        public IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size, string sourceColumn)
        {
            return this._inner.CreateDataParameter(parameterName, dataType, size, sourceColumn);
        }

        public IDbDataParameter CreateDataParameter(string parameterName, object value)
        {
            return this._inner.CreateDataParameter(parameterName, value);
        }
    }
}
