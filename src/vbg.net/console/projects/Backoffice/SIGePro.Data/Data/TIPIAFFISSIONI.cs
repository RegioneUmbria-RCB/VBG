using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIAFFISSIONI")]
	public class TipiAffissioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicetipoaffissione=null;
		[KeyField("CODICETIPOAFFISSIONE", Type=DbType.Decimal)]
		public string CODICETIPOAFFISSIONE
		{
			get { return codicetipoaffissione; }
			set { codicetipoaffissione = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string tipoaffissione=null;
		[DataField("TIPOAFFISSIONE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPOAFFISSIONE
		{
			get { return tipoaffissione; }
			set { tipoaffissione = value; }
		}

	}
}