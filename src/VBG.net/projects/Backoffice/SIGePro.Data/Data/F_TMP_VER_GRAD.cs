using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_TMP_VER_GRAD")]
	public class F_Tmp_Ver_Grad : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string fkid_f_tgraduatorie=null;
		[DataField("FKID_F_TGRADUATORIE", Type=DbType.Decimal)]
		public string FKID_F_TGRADUATORIE
		{
			get { return fkid_f_tgraduatorie; }
			set { fkid_f_tgraduatorie = value; }
		}

		string fkid_f_posteggi=null;
		[DataField("FKID_F_POSTEGGI", Type=DbType.Decimal)]
		public string FKID_F_POSTEGGI
		{
			get { return fkid_f_posteggi; }
			set { fkid_f_posteggi = value; }
		}

		string fkid_f_domande=null;
		[DataField("FKID_F_DOMANDE", Type=DbType.Decimal)]
		public string FKID_F_DOMANDE
		{
			get { return fkid_f_domande; }
			set { fkid_f_domande = value; }
		}

		string cat_merc=null;
		[DataField("CAT_MERC",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CAT_MERC
		{
			get { return cat_merc; }
			set { cat_merc = value; }
		}

		string fkid_f_giorni_fiera=null;
		[DataField("FKID_F_GIORNI_FIERA", Type=DbType.Decimal)]
		public string FKID_F_GIORNI_FIERA
		{
			get { return fkid_f_giorni_fiera; }
			set { fkid_f_giorni_fiera = value; }
		}

		string se_incongruenza=null;
		[DataField("SE_INCONGRUENZA", Type=DbType.Decimal)]
		public string SE_INCONGRUENZA
		{
			get { return se_incongruenza; }
			set { se_incongruenza = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}