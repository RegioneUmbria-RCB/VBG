using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PERMCDSAMMIN")]
	public class PermCdSAmmin : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecds=null;
		[KeyField("CODICECDS", Type=DbType.Decimal)]
		public string CODICECDS
		{
			get { return codicecds; }
			set { codicecds = value; }
		}

		string codiceatto=null;
		[KeyField("CODICEATTO", Type=DbType.Decimal)]
		public string CODICEATTO
		{
			get { return codiceatto; }
			set { codiceatto = value; }
		}

		string codiceamministrazione=null;
		[KeyField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}
	}
}