using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_DGRADUATORIE")]
	public class F_DGraduatorie: BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string dgr_fkid_f_tgraduatorie=null;
		[KeyField("DGR_FKID_F_TGRADUATORIE", Type=DbType.Decimal)]
		public string DGR_FKID_F_TGRADUATORIE
		{
			get { return dgr_fkid_f_tgraduatorie; }
			set { dgr_fkid_f_tgraduatorie = value; }
		}

		string dgr_fkid_f_domande=null;
		[KeyField("DGR_FKID_F_DOMANDE", Type=DbType.Decimal)]
		public string DGR_FKID_F_DOMANDE
		{
			get { return dgr_fkid_f_domande; }
			set { dgr_fkid_f_domande = value; }
		}

		string dgr_ordine=null;
		[DataField("DGR_ORDINE", Type=DbType.Decimal)]
		public string DGR_ORDINE
		{
			get { return dgr_ordine; }
			set { dgr_ordine = value; }
		}

		string dgr_fkid_f_posteggi=null;
		[DataField("DGR_FKID_F_POSTEGGI", Type=DbType.Decimal)]
		public string DGR_FKID_F_POSTEGGI
		{
			get { return dgr_fkid_f_posteggi; }
			set { dgr_fkid_f_posteggi = value; }
		}

		string dgr_fkid_f_giorni_fiera=null;
		[DataField("DGR_FKID_F_GIORNI_FIERA", Type=DbType.Decimal)]
		public string DGR_FKID_F_GIORNI_FIERA
		{
			get { return dgr_fkid_f_giorni_fiera; }
			set { dgr_fkid_f_giorni_fiera = value; }
		}

		string dgr_presenze=null;
		[DataField("DGR_PRESENZE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DGR_PRESENZE
		{
			get { return dgr_presenze; }
			set { dgr_presenze = value; }
		}

		string dgr_data_consegna=null;
		[DataField("DGR_DATA_CONSEGNA",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DGR_DATA_CONSEGNA
		{
			get { return dgr_data_consegna; }
			set { dgr_data_consegna = value; }
		}

		string dgr_data_cciiaa=null;
		[DataField("DGR_DATA_CCIIAA",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DGR_DATA_CCIIAA
		{
			get { return dgr_data_cciiaa; }
			set { dgr_data_cciiaa = value; }
		}
	}
}