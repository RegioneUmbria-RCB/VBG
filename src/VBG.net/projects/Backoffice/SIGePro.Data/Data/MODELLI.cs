using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MODELLI")]
	public class Modelli : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicemodello=null;
		[KeyField("CODICEMODELLO", Type=DbType.Decimal)]
		public string CODICEMODELLO
		{
			get { return codicemodello; }
			set { codicemodello = value; }
		}

		string titolo=null;
		[DataField("TITOLO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
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

		string tiposuap=null;
		[DataField("TIPOSUAP",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string TIPOSUAP
		{
			get { return tiposuap; }
			set { tiposuap = value; }
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