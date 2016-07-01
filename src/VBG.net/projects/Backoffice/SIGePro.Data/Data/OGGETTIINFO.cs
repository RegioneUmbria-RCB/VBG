using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("OGGETTIINFO")]
	public class OggettiInfo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceoggetto=null;
		[KeyField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string codicetipologia=null;
		[DataField("CODICETIPOLOGIA", Type=DbType.Decimal)]
		public string CODICETIPOLOGIA
		{
			get { return codicetipologia; }
			set { codicetipologia = value; }
		}
	}
}