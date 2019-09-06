using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using log4net;
using PersonalLib2.Data;
using PersonalLib2.Data.Providers;

namespace Init.SIGePro.Sit.Ravenna2
{
	class QueryBuilder
	{
		List<QueryTable> _tabelle = new List<QueryTable>();
		List<QueryJoin> _joins = new List<QueryJoin>();
		List<QueryFilter> _filters = new List<QueryFilter>();
		List<QueryField> _selectFields = new List<QueryField>();
		List<QueryField> _orderBy = new List<QueryField>();
		List<IDbDataParameter> _parameters = new List<IDbDataParameter>();
		IProvider _provider;
		DataBase _database;
		
		public QueryBuilder()
		{ 
		}

		public QueryBuilder(DataBase database)
		{
			this._database = database;
			this._provider = database.Specifics;
		}

		public void Select(QueryField field)
		{
			this._selectFields.Add(field);
		}

		public void From(QueryTable table)
		{
			this._tabelle.Add(table);
		}

		public void AddJoin(QueryField field1, QueryField field2)
		{
			this._joins.Add(new QueryJoin(field1, field2));
		}

		public void WhereEqual(QueryField field, string parameterName)
		{
			this._filters.Add(new EqualFilter(field, parameterName));
		}

		public void WhereEqualParametric(QueryField field, object valore)
		{
			var paramName = String.Format("par_{0}_{1}", this._parameters.Count, field.Name);

			this._filters.Add(new EqualFilter(field, this._provider.QueryParameterName(paramName)));
			this.AddParameter(this._database.CreateParameter(paramName, valore));
		}

		internal void OrderBy(QueryField queryField)
		{
			this._orderBy.Add(queryField);
		}

		internal void OrderBy(IEnumerable<QueryField> queryFields)
		{
			this._orderBy.AddRange(queryFields);
		}

		internal void AddParameter(IDbDataParameter parameter)
		{
			this._parameters.Add(parameter);
		}

		internal IEnumerable<object> GetParameters()
		{
			return this._parameters.Cast<object>();
		}

		internal void LogDebug(log4net.ILog log)
		{
			if(!log.IsDebugEnabled)
			{
				return;
			}

			var parametri = new StringBuilder();

			foreach (var par in this._parameters)
			{
				parametri.AppendFormat("\t{0} = \"{1}\" ({2}){3}", par.ParameterName, par.Value, par.DbType, Environment.NewLine);
			}
			
			log.DebugFormat("Query: {0}{1}{1}Parametri:{1}{2}", this.ToString(), Environment.NewLine, parametri.ToString());
		}


		public override string ToString()
		{
			var sb = new StringBuilder();

			var fields = String.Join(", ", this._selectFields.Select( x => x.ToString()));
			var tables = String.Join(", ", this._tabelle.Select( x => x.ToString()));
			var joins = "";
			var filters = "";

			sb.AppendFormat(GetQueryBase(), fields, tables);

			if (this._joins.Count > 0)
			{
				joins = " and " + String.Join(" and ", this._joins.Select(x => x.ToString()));
			}

			if (this._filters.Count > 0)
			{
				filters = " and " + String.Join(" and ", this._filters.Select(x => x.ToString()));
			}

			if (!String.IsNullOrEmpty(joins) || !String.IsNullOrEmpty(filters))
			{
				sb.Append(" where 1=1");
				sb.Append(joins);
				sb.Append(filters);
			}

			if (this._orderBy.Count > 0)
			{
				sb.Append(" order by ");
				sb.Append(String.Join(", ", this._orderBy.Select(x => x.ToString())));
				
			}
						
			return sb.ToString();
		}

		protected virtual string GetQueryBase()
		{
			return "select {0} from {1}";
		}
	}
}
