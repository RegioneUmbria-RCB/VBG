using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class QueryFilter
	{
		private QueryField field;
		private string condition;
		private string operation;

		protected QueryFilter(QueryField field, string operation, string condition)
		{
			// TODO: Complete member initialization
			this.field = field;
			this.condition = condition;
			this.operation = operation;
		}

		public override string ToString()
		{
			return String.Format("{0} {1} {2}", this.field, this.operation, this.condition);
		}
	}
}
