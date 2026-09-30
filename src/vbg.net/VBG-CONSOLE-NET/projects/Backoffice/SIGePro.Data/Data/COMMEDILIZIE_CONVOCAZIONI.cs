using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_CONVOCAZIONI")]
	public class CommEdilizie_Convocazioni : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string codicecommissione=null;
		[KeyField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

        DateTime? dataconvocazione = null;
		[DataField("DATACONVOCAZIONE", Type=DbType.DateTime)]
		public DateTime? DATACONVOCAZIONE
		{
			get { return dataconvocazione; }
            set { dataconvocazione = VerificaDataLocale(value); }
		}

		string oraconvocazione=null;
		[DataField("ORACONVOCAZIONE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string ORACONVOCAZIONE
		{
			get { return oraconvocazione; }
			set { oraconvocazione = value; }
		}

	}
}