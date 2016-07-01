using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_CRITERISTAMPA")]
	public class Tmp_CriteriStampa : BaseDataClass
	{
		string codicecriterio=null;
		[KeyField("CODICECRITERIO", Type=DbType.Decimal)]
		public string CODICECRITERIO
		{
			get { return codicecriterio; }
			set { codicecriterio = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string nomeetichetta=null;
		[DataField("NOMEETICHETTA",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEETICHETTA
		{
			get { return nomeetichetta; }
			set { nomeetichetta = value; }
		}

		string valoreetichetta=null;
		[DataField("VALOREETICHETTA",Size=300, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VALOREETICHETTA
		{
			get { return valoreetichetta; }
			set { valoreetichetta = value; }
		}

		string datacriterio=null;
		[DataField("DATACRITERIO",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string DATACRITERIO
		{
			get { return datacriterio; }
			set { datacriterio = value; }
		}

	}
}