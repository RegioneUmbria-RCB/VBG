using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ONERICOMPORTAMENTO")]
	public class OneriComportamento : BaseDataClass
	{
		string codicecomportamento=null;
		[KeyField("CODICECOMPORTAMENTO", Type=DbType.Decimal)]
		public string CODICECOMPORTAMENTO
		{
			get { return codicecomportamento; }
			set { codicecomportamento = value; }
		}

		string comportamento=null;
		[DataField("COMPORTAMENTO",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string COMPORTAMENTO
		{
			get { return comportamento; }
			set { comportamento = value; }
		}

	}
}