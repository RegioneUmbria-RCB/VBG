using System;
using System.Collections.Generic;
using System.Data;

namespace PersonalLib2.Data
{
    public class DatabaseCommandParameterFactory : ICommandParameterFactory
    {
        private readonly List<IDbDataParameter> _parameters = new List<IDbDataParameter>();
        private readonly DataBase _db;

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

        public ICommandParameterFactory Add(string parameterName, object value)
        {
            this.AddParameter(parameterName, value);

            return this;
        }
    }
}
