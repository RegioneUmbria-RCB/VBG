using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using PersonalLib2.Data.Providers;

namespace Init.SIGePro.Sit.Ravenna2
{
	class DistinctQueryBuilder : QueryBuilder
	{
		public DistinctQueryBuilder():base()
		{
		}

		public DistinctQueryBuilder(DataBase database)
			: base(database)
		{
		}

		protected override string GetQueryBase()
		{
			return "select distinct {0} from {1}";
		}
	}
}
