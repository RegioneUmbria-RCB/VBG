using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SIGEPROPAGES")]
	public class SigeproPages : BaseDataClass
	{
		string sp_id=null;
		[DataField("SP_ID", Type=DbType.Decimal)]
		public string SP_ID
		{
			get { return sp_id; }
			set { sp_id = value; }
		}

		string sp_page=null;
		[DataField("SP_PAGE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SP_PAGE
		{
			get { return sp_page; }
			set { sp_page = value; }
		}

	}
}