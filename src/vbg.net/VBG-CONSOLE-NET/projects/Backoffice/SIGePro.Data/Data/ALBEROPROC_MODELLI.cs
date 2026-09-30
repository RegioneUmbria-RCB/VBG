using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("ALBEROPROC_MODELLI")]
	[Serializable]
	public class AlberoProcModelli : BaseDataClass
	{

		#region Key Fields

		string sm_id=null;
		[useSequence]
		[KeyField("SM_ID", Type=DbType.Decimal)]
		public string SM_ID
		{
			get { return sm_id; }
			set { sm_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion
		
		string sm_fkscid=null;
		[DataField("SM_FKSCID", Type=DbType.Decimal)]
		public string SM_FKSCID
		{
			get { return sm_fkscid; }
			set { sm_fkscid = value; }
		}

		string sm_fkmoid=null;
		[DataField("SM_FKMOID", Type=DbType.Decimal)]
		public string SM_FKMOID
		{
			get { return sm_fkmoid; }
			set { sm_fkmoid = value; }
		}
	}
}