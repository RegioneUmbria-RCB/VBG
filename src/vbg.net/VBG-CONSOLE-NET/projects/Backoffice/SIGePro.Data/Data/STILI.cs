using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("STILI")]
	public class Stili : BaseDataClass
	{
		string st_id=null;
		[DataField("ST_ID", Type=DbType.Decimal)]
		public string ST_ID
		{
			get { return st_id; }
			set { st_id = value; }
		}

		string st_descrizione=null;
		[DataField("ST_DESCRIZIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ST_DESCRIZIONE
		{
			get { return st_descrizione; }
			set { st_descrizione = value; }
		}

		string st_nomefile=null;
		[DataField("ST_NOMEFILE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ST_NOMEFILE
		{
			get { return st_nomefile; }
			set { st_nomefile = value; }
		}

	}
}