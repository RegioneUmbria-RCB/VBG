using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FO_CONTESTIBASE")]
	public class Fo_ContestiBase : BaseDataClass
	{
		string id=null;
		[KeyField("ID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string valore=null;
		[DataField("VALORE",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VALORE
		{
			get { return valore; }
			set { valore = value; }
		}

	}
}