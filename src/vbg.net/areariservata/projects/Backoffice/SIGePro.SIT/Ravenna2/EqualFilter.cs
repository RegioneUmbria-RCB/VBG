using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class EqualFilter : QueryFilter
	{
		public EqualFilter(QueryField field, string value):
			base(field, "=", value)
		{

		}
	}
}
