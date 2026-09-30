using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INFO")]
	public class Info : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinfo=null;
		[KeyField("CODICEINFO", Type=DbType.Decimal)]
		public string CODICEINFO
		{
			get { return codiceinfo; }
			set { codiceinfo = value; }
		}

		string titolo=null;
		[DataField("TITOLO",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}

		string ordine=null;
		[DataField("ORDINE", Type=DbType.Decimal)]
		public string ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

		string indirizzoweb=null;
		[DataField("INDIRIZZOWEB",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZOWEB
		{
			get { return indirizzoweb; }
			set { indirizzoweb = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}
	}
}