using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TEMPLETTERECDS")]
	public class TempLettereCdS : BaseDataClass
	{
		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string corpo=null;
		[DataField("CORPO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CORPO
		{
			get { return corpo; }
			set { corpo = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

	}
}