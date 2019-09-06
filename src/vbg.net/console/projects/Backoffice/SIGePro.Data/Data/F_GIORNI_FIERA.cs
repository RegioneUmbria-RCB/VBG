using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_GIORNI_FIERA")]
	public class F_Giorni_Fiera : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string gg_id=null;
		[KeyField("GG_ID", Type=DbType.Decimal)]
		public string GG_ID
		{
			get { return gg_id; }
			set { gg_id = value; }
		}

		string gg_fkid_f_fiere=null;
		[DataField("GG_FKID_F_FIERE", Type=DbType.Decimal)]
		public string GG_FKID_F_FIERE
		{
			get { return gg_fkid_f_fiere; }
			set { gg_fkid_f_fiere = value; }
		}

		string gg_peso=null;
		[DataField("GG_PESO", Type=DbType.Decimal)]
		public string GG_PESO
		{
			get { return gg_peso; }
			set { gg_peso = value; }
		}

		string gg_data=null;
		[DataField("GG_DATA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string GG_DATA
		{
			get { return gg_data; }
			set { gg_data = value; }
		}
	}
}