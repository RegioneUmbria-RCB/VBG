using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
namespace Init.SIGePro.Data
{
	[DataTable("COMUNISECURITY_CONNECTION")]
	[Serializable]
	public class ComuniSecurity_Connection : BaseDataClass
	{
		string cs_codiceistat=null;
		[KeyField("FK_CS_CODICEISTAT",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CS_CONNECTION_CODICEISTAT
		{
			get { return cs_codiceistat; }
			set { cs_codiceistat = value; }
		}

		string cs_conn_ambiente=null;
		[KeyField("AMBIENTE",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string CS_CONNECTION_AMBIENTE
		{
			get { return cs_conn_ambiente; }
			set { cs_conn_ambiente = value; }
		}

		string cs_conn_connectionstring=null;
		[DataField("CONNECTIONSTRING",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CS_CONNECTION_CONNECTIONSTRING
		{
			get { return cs_conn_connectionstring; }
			set { cs_conn_connectionstring = value; }
		}

		string cs_conn_provider=null;
		[DataField("PROVIDER",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string CS_CONNECTION_PROVIDER
		{
			get { return cs_conn_provider; }
			set { cs_conn_provider = value; }
		}
	}
}
