using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PROFILIPAGES")]
	public class ProfiliPages : BaseDataClass
	{
		string pp_opid=null;
		[DataField("PP_OPID", Type=DbType.Decimal)]
		public string PP_OPID
		{
			get { return pp_opid; }
			set { pp_opid = value; }
		}

		string pp_spid=null;
		[DataField("PP_SPID", Type=DbType.Decimal)]
		public string PP_SPID
		{
			get { return pp_spid; }
			set { pp_spid = value; }
		}

	}
}