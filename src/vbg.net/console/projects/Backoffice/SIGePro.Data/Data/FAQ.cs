using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FAQ")]
	public class Faq : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicefaq=null;
		[KeyField("CODICEFAQ", Type=DbType.Decimal)]
		public string CODICEFAQ
		{
			get { return codicefaq; }
			set { codicefaq = value; }
		}

		DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string domanda=null;
		[DataField("DOMANDA",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DOMANDA
		{
			get { return domanda; }
			set { domanda = value; }
		}

		string risposta=null;
		[DataField("RISPOSTA",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RISPOSTA
		{
			get { return risposta; }
			set { risposta = value; }
		}

		string pubblicare=null;
		[DataField("PUBBLICARE", Type=DbType.Decimal)]
		public string PUBBLICARE
		{
			get { return pubblicare; }
			set { pubblicare = value; }
		}

		string codicefaqclasse=null;
		[DataField("CODICEFAQCLASSE", Type=DbType.Decimal)]
		public string CODICEFAQCLASSE
		{
			get { return codicefaqclasse; }
			set { codicefaqclasse = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}