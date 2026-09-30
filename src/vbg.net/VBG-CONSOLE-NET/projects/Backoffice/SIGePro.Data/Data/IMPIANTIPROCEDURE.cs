using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("IMPIANTIPROCEDURE")]
	public class ImpiantiProcedure : BaseDataClass
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

		string codiceprocedura=null;
		[KeyField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
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