using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class CompositeQueryField : QueryField
	{
		IEnumerable<QueryField> _fields;

		public CompositeQueryField(QueryTable tableName, string[] fields)
			: base(tableName)
		{
			this._fields = fields.Select(x => new QueryField(tableName, x));
		}

		public override string Name
		{
			get
			{
				return String.Join("_", this._fields.Select(x => x.Name).ToArray());
			}
		}

		public override string ToString()
		{
			return String.Join(", ", this._fields.Select(x => x.ToString()).ToArray());
		}
	}
}
