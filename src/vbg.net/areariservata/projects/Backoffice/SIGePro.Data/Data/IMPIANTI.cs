using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("IMPIANTI")]
	public class Impianti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceimpianto=null;
		[KeyField("CODICEIMPIANTO", Type=DbType.Decimal)]
		public string CODICEIMPIANTO
		{
			get { return codiceimpianto; }
			set { codiceimpianto = value; }
		}

		string impianto=null;
		[DataField("IMPIANTO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMPIANTO
		{
			get { return impianto; }
			set { impianto = value; }
		}

		string note=null;
		[DataField("NOTE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}