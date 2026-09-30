using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("RESPONSABILICOMUNI")]
	public class ResponsabiliComuni : BaseDataClass
	{
		string codiceresponsabile=null;
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string codicecomune=null;
		[DataField("CODICECOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CODICECOMUNE
		{
			get { return codicecomune; }
			set { codicecomune = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}