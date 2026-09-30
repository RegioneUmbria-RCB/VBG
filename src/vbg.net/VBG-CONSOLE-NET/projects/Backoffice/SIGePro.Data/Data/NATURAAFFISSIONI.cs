using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("NATURAAFFISSIONI")]
	public class NaturaAffissioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicenatura=null;
		[KeyField("CODICENATURA", Type=DbType.Decimal)]
		public string CODICENATURA
		{
			get { return codicenatura; }
			set { codicenatura = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string natura=null;
		[DataField("NATURA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NATURA
		{
			get { return natura; }
			set { natura = value; }
		}

	}
}