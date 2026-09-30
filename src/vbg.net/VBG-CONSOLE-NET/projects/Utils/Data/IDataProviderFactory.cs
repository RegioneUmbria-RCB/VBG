using PersonalLib2.Data.Providers;
using System.Data;

namespace PersonalLib2.Data
{
    public interface IDataProviderFactory
    {
        ProviderType Provider { get; }
        IProvider Specifics { get; }

        IDbCommand CreateCommand();
        IDbCommand CreateCommand(string cmdText);
        IDbCommand CreateCommand(string cmdText, IDbConnection connection);
        IDbCommand CreateCommand(string cmdText, IDbConnection connection, IDbTransaction transaction);
        IDbConnection CreateConnection();
        IDbConnection CreateConnection(string connectionString);
        IDbDataAdapter CreateDataAdapter();
        IDbDataAdapter CreateDataAdapter(IDbCommand selectCommand);
        IDbDataAdapter CreateDataAdapter(string selectCommandText, IDbConnection selectConnection);
        IDbDataAdapter CreateDataAdapter(string selectCommandText, string selectConnectionString);
        IDbDataParameter CreateDataParameter();
        IDbDataParameter CreateDataParameter(string parameterName, DbType dataType);
        IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size);
        IDbDataParameter CreateDataParameter(string parameterName, DbType dataType, int size, string sourceColumn);
        IDbDataParameter CreateDataParameter(string parameterName, object value);
    }
}