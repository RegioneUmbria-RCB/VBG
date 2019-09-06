using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_TMP_ASS_POSTEGGI")]
	public class F_Tmp_Ass_Posteggi : BaseDataClass
	{
		string ap_id=null;
		[DataField("AP_ID", Type=DbType.Decimal)]
		public string AP_ID
		{
			get { return ap_id; }
			set { ap_id = value; }
		}

		string ap_fkid_f_posteggi=null;
		[DataField("AP_FKID_F_POSTEGGI", Type=DbType.Decimal)]
		public string AP_FKID_F_POSTEGGI
		{
			get { return ap_fkid_f_posteggi; }
			set { ap_fkid_f_posteggi = value; }
		}

		string ap_fkid_f_domande=null;
		[DataField("AP_FKID_F_DOMANDE", Type=DbType.Decimal)]
		public string AP_FKID_F_DOMANDE
		{
			get { return ap_fkid_f_domande; }
			set { ap_fkid_f_domande = value; }
		}

		string ap_fkid_f_giorni_fiera=null;
		[DataField("AP_FKID_F_GIORNI_FIERA", Type=DbType.Decimal)]
		public string AP_FKID_F_GIORNI_FIERA
		{
			get { return ap_fkid_f_giorni_fiera; }
			set { ap_fkid_f_giorni_fiera = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
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