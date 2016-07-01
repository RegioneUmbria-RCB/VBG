using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_TGRADUATORIE")]
	public class F_TGraduatorie : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string gr_id=null;
		[KeyField("GR_ID", Type=DbType.Decimal)]
		public string GR_ID
		{
			get { return gr_id; }
			set { gr_id = value; }
		}

		string gr_descrizione=null;
		[DataField("GR_DESCRIZIONE",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string GR_DESCRIZIONE
		{
			get { return gr_descrizione; }
			set { gr_descrizione = value; }
		}

		string gr_fkid_f_categorie=null;
		[DataField("GR_FKID_F_CATEGORIE", Type=DbType.Decimal)]
		public string GR_FKID_F_CATEGORIE
		{
			get { return gr_fkid_f_categorie; }
			set { gr_fkid_f_categorie = value; }
		}

		string gr_fkid_f_fiere=null;
		[DataField("GR_FKID_F_FIERE", Type=DbType.Decimal)]
		public string GR_FKID_F_FIERE
		{
			get { return gr_fkid_f_fiere; }
			set { gr_fkid_f_fiere = value; }
		}

	}
}