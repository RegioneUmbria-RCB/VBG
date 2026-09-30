using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("EMAIL")]
	public class Email : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceemail=null;
		[KeyField("CODICEEMAIL", Type=DbType.Decimal)]
		public string CODICEEMAIL
		{
			get { return codiceemail; }
			set { codiceemail = value; }
		}

		string codiceamministrazione=null;
		[KeyField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string testo=null;
		[DataField("TESTO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TESTO
		{
			get { return testo; }
			set { testo = value; }
		}

		string codiceufficio=null;
		[DataField("CODICEUFFICIO", Type=DbType.Decimal)]
		public string CODICEUFFICIO
		{
			get { return codiceufficio; }
			set { codiceufficio = value; }
		}
	}
}