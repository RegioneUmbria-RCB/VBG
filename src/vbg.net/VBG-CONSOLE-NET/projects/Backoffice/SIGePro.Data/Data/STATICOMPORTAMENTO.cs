using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("STATICOMPORTAMENTO")]
	public class StatiComportamento : BaseDataClass
	{
		string codcomportamento=null;
		[KeyField("CODCOMPORTAMENTO", Type=DbType.Decimal)]
		public string CODCOMPORTAMENTO
		{
			get { return codcomportamento; }
			set { codcomportamento = value; }
		}

		string comportamento=null;
		[DataField("COMPORTAMENTO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string COMPORTAMENTO
		{
			get { return comportamento; }
			set { comportamento = value; }
		}

	}
}