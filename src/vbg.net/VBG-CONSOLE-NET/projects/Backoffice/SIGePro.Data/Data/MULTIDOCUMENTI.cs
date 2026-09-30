using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MULTIDOCUMENTI")]
	public class MultiDocumenti: BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicemuldoc=null;
		[KeyField("CODICEMULDOC", Type=DbType.Decimal)]
		public string CODICEMULDOC
		{
			get { return codicemuldoc; }
			set { codicemuldoc = value; }
		}

		string descrizionemuldoc=null;
		[DataField("DESCRIZIONEMULDOC",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONEMULDOC
		{
			get { return descrizionemuldoc; }
			set { descrizionemuldoc = value; }
		}
	}
}