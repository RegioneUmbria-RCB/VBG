using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC_LEGGI")]
	public class Alberoproc_Leggi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string sl_id=null;
		[KeyField("SL_ID", Type=DbType.Decimal)]
		public string SL_ID
		{
			get { return sl_id; }
			set { sl_id = value; }
		}

		string sl_fkscid=null;
		[DataField("SL_FKSCID", Type=DbType.Decimal)]
		public string SL_FKSCID
		{
			get { return sl_fkscid; }
			set { sl_fkscid = value; }
		}

		string sl_fkleid=null;
		[DataField("SL_FKLEID", Type=DbType.Decimal)]
		public string SL_FKLEID
		{
			get { return sl_fkleid; }
			set { sl_fkleid = value; }
		}

	}
}