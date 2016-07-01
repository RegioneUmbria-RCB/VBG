using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_POSTEGGI_CAT")]
	public class F_Posteggi_Cat : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string pc_fkid_f_posteggi=null;
		[KeyField("PC_FKID_F_POSTEGGI", Type=DbType.Decimal)]
		public string PC_FKID_F_POSTEGGI
		{
			get { return pc_fkid_f_posteggi; }
			set { pc_fkid_f_posteggi = value; }
		}

		string pc_fkid_f_categorie=null;
		[KeyField("PC_FKID_F_CATEGORIE", Type=DbType.Decimal)]
		public string PC_FKID_F_CATEGORIE
		{
			get { return pc_fkid_f_categorie; }
			set { pc_fkid_f_categorie = value; }
		}
	}
}