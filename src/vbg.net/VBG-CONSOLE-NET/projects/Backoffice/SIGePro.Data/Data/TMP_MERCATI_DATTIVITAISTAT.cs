using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_MERCATI_DATTIVITAISTAT")]
	public class Tmp_Merc : BaseDataClass
	{
		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string fkcodiceattivitaistat=null;
		[DataField("FKCODICEATTIVITAISTAT",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FKCODICEATTIVITAISTAT
		{
			get { return fkcodiceattivitaistat; }
			set { fkcodiceattivitaistat = value; }
		}

		string flag_consentito=null;
		[DataField("FLAG_CONSENTITO", Type=DbType.Decimal)]
		public string FLAG_CONSENTITO
		{
			get { return flag_consentito; }
			set { flag_consentito = value; }
		}

	}
}