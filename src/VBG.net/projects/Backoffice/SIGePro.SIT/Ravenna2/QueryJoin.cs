using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class QueryJoin
	{
		QueryField _f1;
		QueryField _f2;

		public QueryJoin(QueryField f1, QueryField f2)
		{
			this._f1 = f1;
			this._f2 = f2;
		}

		public override string ToString()
		{
			return String.Format("{0} = {1}", this._f1, this._f2);
		}
	}
}
