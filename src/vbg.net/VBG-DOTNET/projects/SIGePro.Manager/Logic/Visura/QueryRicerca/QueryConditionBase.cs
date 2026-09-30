using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class QueryConditionBase : IQueryCondition
    {
        protected string Query = String.Empty;
        private List<ParameterDefinition> Parameters = new List<ParameterDefinition>();
        private readonly DataBase _database;
        private readonly string _prefix;

        protected QueryConditionBase(DataBase database, string prefix)
        {
            _database = database;
            _prefix = prefix + "_";
        }

        private string Prefix(string name)
        {
            return this._prefix + name;
        }

        protected string QueryParameterName(string name)
        {
            return this._database.Specifics.QueryParameterName(Prefix(name));
        }

        protected void AddParameter(string paramName, object paramValue)
        {
            var param = new ParameterDefinition(Prefix(paramName), paramValue);

            this.Parameters.Add(param);
        }

        public IEnumerable<ParameterDefinition> GetParameters()
        {
            return this.Parameters;
        }

        public string GetQuery()
        {
            return this.Query;
        }

        public void VerifyParameters()
        {
            foreach(var parName in this.Parameters.Select( x => x.ParameterName))
            {
                if (this.Query.IndexOf(parName) == -1)
                {
                    throw new ParameterIncorrectlySetException($"Il parametro {parName} on è stato trovato nella query {this.Query}");
                }

                if (parName.Length > 30)
                    throw new InvalidParameterNameException($"Il nome parametro {parName} è troppo lungo");
            }
        }
    }
}
