using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class Ravenna2ResultSet
	{
		private List<string> _result = new List<string>();

		public Ravenna2ResultSet(System.Data.IDataReader dr)
		{
			while(dr.Read())
			{
				this._result.Add(dr[0].ToString());
			}
		}
		internal Data.RetSit ToRetSit()
		{
			return new Data.RetSit(true, this._result);
		}
	}
}
