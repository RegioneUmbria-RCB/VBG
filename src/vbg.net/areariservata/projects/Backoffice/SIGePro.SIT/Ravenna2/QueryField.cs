using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class QueryField
	{
		protected QueryTable _tableName;
		string _fieldName;

		protected QueryField(QueryTable tableName)
		{

		}

		public QueryField(QueryTable tableName, string fieldName)
		{
			this._tableName = tableName;
			this._fieldName = fieldName;
		}

		public virtual string Name
		{
			get { return this._fieldName; }
		}

		public override string ToString()
		{
			return String.Format("{0}.{1}", this._tableName, this._fieldName);
		}
	}
}
