using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_CONCESSIONISTORICO")]
	public class Tmp_ConcessioniStorico : BaseDataClass
	{
		string threadid=null;
		[DataField("THREADID",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string THREADID
		{
			get { return threadid; }
			set { threadid = value; }
		}

		string id=null;
		[DataField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string ordine=null;
		[DataField("ORDINE", Type=DbType.Decimal)]
		public string ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

	}
}