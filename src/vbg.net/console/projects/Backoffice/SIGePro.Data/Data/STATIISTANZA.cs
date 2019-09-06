using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	/*[DataTable("STATIISTANZA")]*/
	public partial class StatiIstanza /*: BaseDataClass*/
	{
		public override string ToString()
		{
			return Stato;
		}
	}
}