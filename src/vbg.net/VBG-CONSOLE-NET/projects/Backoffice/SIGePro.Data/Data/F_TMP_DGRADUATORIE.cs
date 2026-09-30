using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_TMP_DGRADUATORIE")]
	public class F_Tmp_DGraduatorie : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string tgr_ordine=null;
		[DataField("TGR_ORDINE", Type=DbType.Decimal)]
		public string TGR_ORDINE
		{
			get { return tgr_ordine; }
			set { tgr_ordine = value; }
		}

		string tgr_fkid_f_domande=null;
		[DataField("TGR_FKID_F_DOMANDE", Type=DbType.Decimal)]
		public string TGR_FKID_F_DOMANDE
		{
			get { return tgr_fkid_f_domande; }
			set { tgr_fkid_f_domande = value; }
		}

		string tgr_fkid_f_posteggi=null;
		[DataField("TGR_FKID_F_POSTEGGI", Type=DbType.Decimal)]
		public string TGR_FKID_F_POSTEGGI
		{
			get { return tgr_fkid_f_posteggi; }
			set { tgr_fkid_f_posteggi = value; }
		}

		string tgr_fkid_f_giorni_fiera=null;
		[DataField("TGR_FKID_F_GIORNI_FIERA", Type=DbType.Decimal)]
		public string TGR_FKID_F_GIORNI_FIERA
		{
			get { return tgr_fkid_f_giorni_fiera; }
			set { tgr_fkid_f_giorni_fiera = value; }
		}

		string tgr_presenze=null;
		[DataField("TGR_PRESENZE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TGR_PRESENZE
		{
			get { return tgr_presenze; }
			set { tgr_presenze = value; }
		}

		string tgr_data_consegna=null;
		[DataField("TGR_DATA_CONSEGNA",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TGR_DATA_CONSEGNA
		{
			get { return tgr_data_consegna; }
			set { tgr_data_consegna = value; }
		}

		string tgr_data_cciiaa=null;
		[DataField("TGR_DATA_CCIIAA",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TGR_DATA_CCIIAA
		{
			get { return tgr_data_cciiaa; }
			set { tgr_data_cciiaa = value; }
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