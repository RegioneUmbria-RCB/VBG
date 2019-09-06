using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_TMP_GR")]
	public class F_Tmp_Gr : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string dm_id=null;
		[DataField("DM_ID", Type=DbType.Decimal)]
		public string DM_ID
		{
			get { return dm_id; }
			set { dm_id = value; }
		}

		string ord1=null;
		[DataField("ORD1",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ORD1
		{
			get { return ord1; }
			set { ord1 = value; }
		}

		string ord2=null;
		[DataField("ORD2",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ORD2
		{
			get { return ord2; }
			set { ord2 = value; }
		}

		string ord3=null;
		[DataField("ORD3",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ORD3
		{
			get { return ord3; }
			set { ord3 = value; }
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