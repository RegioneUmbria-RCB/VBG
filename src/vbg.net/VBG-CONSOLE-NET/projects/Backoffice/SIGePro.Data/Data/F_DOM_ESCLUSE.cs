using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_DOM_ESCLUSE")]
	public class F_Dom_Escluse : BaseDataClass
	{
		string dm_id=null;
		[DataField("DM_ID", Type=DbType.Decimal)]
		public string DM_ID
		{
			get { return dm_id; }
			set { dm_id = value; }
		}

		string motivo_esclusione=null;
		[DataField("MOTIVO_ESCLUSIONE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOTIVO_ESCLUSIONE
		{
			get { return motivo_esclusione; }
			set { motivo_esclusione = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
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