using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_FIERE")]
	public class F_Fiere : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string fi_id=null;
		[KeyField("FI_ID", Type=DbType.Decimal)]
		public string FI_ID
		{
			get { return fi_id; }
			set { fi_id = value; }
		}

		string fi_descrizione=null;
		[DataField("FI_DESCRIZIONE",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FI_DESCRIZIONE
		{
			get { return fi_descrizione; }
			set { fi_descrizione = value; }
		}

		string fi_dal=null;
		[DataField("FI_DAL",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FI_DAL
		{
			get { return fi_dal; }
			set { fi_dal = value; }
		}

		string fi_al=null;
		[DataField("FI_AL",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string FI_AL
		{
			get { return fi_al; }
			set { fi_al = value; }
		}
	}
}