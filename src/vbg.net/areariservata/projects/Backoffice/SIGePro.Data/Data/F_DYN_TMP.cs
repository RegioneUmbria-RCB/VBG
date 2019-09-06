using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_DYN_TMP")]
	public class F_Dyn_Tmp : BaseDataClass
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

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}