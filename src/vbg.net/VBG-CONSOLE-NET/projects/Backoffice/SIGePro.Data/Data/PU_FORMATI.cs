using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PU_FORMATI")]
	public class Pu_Formati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string fo_id=null;
		[KeyField("FO_ID", Type=DbType.Decimal)]
		public string FO_ID
		{
			get { return fo_id; }
			set { fo_id = value; }
		}

		string fo_base=null;
		[DataField("FO_BASE", Type=DbType.Decimal)]
		public string FO_BASE
		{
			get { return fo_base; }
			set { fo_base = value; }
		}

		string fo_altezza=null;
		[DataField("FO_ALTEZZA", Type=DbType.Decimal)]
		public string FO_ALTEZZA
		{
			get { return fo_altezza; }
			set { fo_altezza = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string fo_facce=null;
		[DataField("FO_FACCE", Type=DbType.Decimal)]
		public string FO_FACCE
		{
			get { return fo_facce; }
			set { fo_facce = value; }
		}

	}
}