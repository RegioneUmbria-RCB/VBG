using PersonalLib2.Data.Providers;
using System;
using System.Collections.Generic;
using System.Data;

namespace PersonalLib2.Data
{
    public interface IDatabase : IDisposable
    {
        IProvider Specifics { get; }
        string QueryParameter(string parameterName);
        [Obsolete("Utilizzare il metodo che prende una formattablestring")]
        int ExecuteNonQuery(string sql, Action<ICommandParameterFactory> callback);
        int ExecuteNonQuery(FormattableString sql);
        IEnumerable<T> ExecuteReader<T>(FormattableString sql, Func<IDataReader, T> mapItem);
        // [Obsolete("Utilizzare il metodo che prende una formattablestring")]
        IEnumerable<T> ExecuteReader<T>(string sql, Action<ICommandParameterFactory> callback, Func<IDataReader, T> mapItem);
        //[Obsolete("Utilizzare il metodo che prende una formattablestring")]
        T ExecuteScalar<T>(string sql, T defaultValue, Action<ICommandParameterFactory> callback);
        T ExecuteScalar<T>(FormattableString sql, T defaultValue);
        void CommitTransaction();
        void RollbackTransaction();
        void BeginTransaction();

        bool IsInTransaction { get; }
    }
}