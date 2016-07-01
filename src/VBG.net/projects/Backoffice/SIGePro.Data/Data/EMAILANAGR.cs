using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("EMAILANAGR")]
	public class EmailAnagr : BaseDataClass
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

        string codiceanagrafica = null;
		[KeyField("CODICEANAGRAFICA", Type=DbType.Decimal)]
        public string CODICEANAGRAFICA
		{
			get { return codiceanagrafica; }
			set { codiceanagrafica = value; }
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
	}
}