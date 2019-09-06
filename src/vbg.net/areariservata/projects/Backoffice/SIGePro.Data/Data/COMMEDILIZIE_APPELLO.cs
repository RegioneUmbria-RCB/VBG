using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMEDILIZIE_APPELLO")]
	public class CommEdilizie_Appello : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecommissione=null;
		[KeyField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

		string codiceresponsabile=null;
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string codicecarica=null;
		[DataField("CODICECARICA", Type=DbType.Decimal)]
		public string CODICECARICA
		{
			get { return codicecarica; }
			set { codicecarica = value; }
		}

		string presente=null;
		[DataField("PRESENTE", Type=DbType.Decimal)]
		public string PRESENTE
		{
			get { return presente; }
			set { presente = value; }
		}

	}
}