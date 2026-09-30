using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_ST_ONERI")]
	public class Tmp_St_Oneri : BaseDataClass
	{
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

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

        DateTime? scadenza = null;
		[DataField("SCADENZA", Type=DbType.DateTime)]
		public DateTime? SCADENZA
		{
			get { return scadenza; }
            set { scadenza = VerificaDataLocale(value); }
		}

		string fk_rco_id=null;
		[DataField("FK_RCO_ID", Type=DbType.Decimal)]
		public string FK_RCO_ID
		{
			get { return fk_rco_id; }
			set { fk_rco_id = value; }
		}

		string totale=null;
		[DataField("TOTALE", Type=DbType.Decimal)]
		public string TOTALE
		{
			get { return totale; }
			set { totale = value; }
		}

		string totaleistruttoria=null;
		[DataField("TOTALEISTRUTTORIA", Type=DbType.Decimal)]
		public string TOTALEISTRUTTORIA
		{
			get { return totaleistruttoria; }
			set { totaleistruttoria = value; }
		}

		string fk_co_id=null;
		[DataField("FK_CO_ID", Type=DbType.Decimal)]
		public string FK_CO_ID
		{
			get { return fk_co_id; }
			set { fk_co_id = value; }
		}

	}
}