using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DATASTRADARIO")]
	public class DataStradario : BaseDataClass
	{
        DateTime? ts = null;
		[DataField("TS", Type=DbType.DateTime)]
		public DateTime? TS
		{
			get { return ts; }
            set { ts = VerificaDataLocale(value); }
		}

		string cs_date=null;
		[DataField("CS_DATE",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string CS_DATE
		{
			get { return cs_date; }
			set { cs_date = value; }
		}

		string codistat=null;
		[KeyField("CODISTAT",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CODISTAT
		{
			get { return codistat; }
			set { codistat = value; }
		}

		string upd=null;
		[DataField("UPD",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string UPD
		{
			get { return upd; }
			set { upd = value; }
		}

	}
}