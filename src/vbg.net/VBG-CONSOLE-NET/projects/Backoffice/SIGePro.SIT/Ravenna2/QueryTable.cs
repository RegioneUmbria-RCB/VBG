using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class QueryTable
	{
		string _tableName;
		string _prefix;

		public QueryTable(string tableName, string prefix = "")
		{
			this._tableName = tableName;
			this._prefix = prefix;
		}

		public QueryField GetField(string fieldName)
		{
			return new QueryField(this, fieldName);
		}

		public virtual QueryField AllFields()
		{
			return new QueryField(this, "*");
		}

		public override string ToString()
		{
			if (String.IsNullOrEmpty(this._prefix))
				return this._tableName;

			return String.Format("{0}.{1}", this._prefix, this._tableName);
		}
	}
}
