using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager
{
    public interface ICommandParameterFactory
    {
        void AddParameter(string name, object value);
    }

    public class DatabaseCommandParameterFactory : ICommandParameterFactory
    {
        List<IDbDataParameter> _parameters = new List<IDbDataParameter>();
        DataBase _db;

        public DatabaseCommandParameterFactory(DataBase db)
        {
            this._db = db;
        }

        public void AddParameter(string name, object value)
        {
            if (value == null)
            {
                value = DBNull.Value;
            }

            this._parameters.Add(this._db.CreateParameter(name, value));
        }

        internal IEnumerable<IDbDataParameter> GetParameters()
        {
            return this._parameters;
        }
    }


    public static class DatabaseExtensions
    {
        //public static T ExecuteCommand<T>(this DataBase db, string sql, Action<ICommandParameterFactory> callback, Func<IDbCommand, T> commandCallback)
        //{
        //    var parametersfactory = new DatabaseCommandParameterFactory(db);

        //    if (callback != null)
        //    {
        //        callback(parametersfactory);
        //    }

        //    bool closeConnection = false;

        //    if (db.Connection.State == ConnectionState.Closed)
        //    {
        //        db.Connection.Open();
        //        closeConnection = true;
        //    }

        //    try
        //    {
        //        using (var cmd = db.CreateCommand(sql))
        //        {
        //            foreach (var parameter in parametersfactory.GetParameters())
        //            {
        //                cmd.Parameters.Add(parameter);
        //            }

        //            return commandCallback(cmd);
        //        }
        //    }
        //    finally
        //    {
        //        if (closeConnection)
        //        {
        //            db.Connection.Close();
        //        }
        //    }
        //}
        public static IEnumerable<T> ExecuteReader<T>(this DataBase db, string sql, Action<ICommandParameterFactory> callback, Func<IDataReader, T> mapItem)
        {
            var parametersfactory = new DatabaseCommandParameterFactory(db);

            if (callback != null)
            {
                callback(parametersfactory);
            }

            bool closeConnection = false;

            if (db.Connection.State == ConnectionState.Closed)
            {
                db.Connection.Open();
                closeConnection = true;
            }

            try
            {
                var parameterNames = parametersfactory.GetParameters()
                                                       .Select(x => x.ParameterName)
                                                       .ToArray();

                sql = String.Format(sql, parameterNames);

                using (var cmd = db.CreateCommand(sql))
                {
                    foreach (var parameter in parametersfactory.GetParameters())
                    {
                        cmd.Parameters.Add(parameter);
                    }

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<T>();

                        while (dr.Read())
                        {
                            rVal.Add(mapItem(dr));
                        }

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeConnection)
                {
                    db.Connection.Close();
                }
            }
        }
    }
}
