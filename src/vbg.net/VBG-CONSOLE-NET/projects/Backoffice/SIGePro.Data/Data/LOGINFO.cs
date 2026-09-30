using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LOGINFO")]
	public class LogInfo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string li_id=null;
		[KeyField("LI_ID", Type=DbType.Decimal)]
		public string LI_ID
		{
			get { return li_id; }
			set { li_id = value; }
		}

		string li_op=null;
		[DataField("LI_OP",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string LI_OP
		{
			get { return li_op; }
			set { li_op = value; }
		}

		string li_tab=null;
		[DataField("LI_TAB",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LI_TAB
		{
			get { return li_tab; }
			set { li_tab = value; }
		}

		string li_col=null;
		[DataField("LI_COL",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LI_COL
		{
			get { return li_col; }
			set { li_col = value; }
		}

		string li_pag=null;
		[DataField("LI_PAG",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LI_PAG
		{
			get { return li_pag; }
			set { li_pag = value; }
		}

		string li_desc=null;
		[DataField("LI_DESC",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string LI_DESC
		{
			get { return li_desc; }
			set { li_desc = value; }
		}
	}
}