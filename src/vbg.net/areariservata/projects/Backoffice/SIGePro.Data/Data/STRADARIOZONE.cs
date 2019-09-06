using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("STRADARIOZONE")]
	public class StradarioZone : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicezona=null;
		[KeyField("CODICEZONA", Type=DbType.Decimal)]
		public string CODICEZONA
		{
			get { return codicezona; }
			set { codicezona = value; }
		}

		string zona=null;
		[DataField("ZONA",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ZONA
		{
			get { return zona; }
			set { zona = value; }
		}
	}
}