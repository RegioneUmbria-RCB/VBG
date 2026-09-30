using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_ALLEGATI")]
	public class Dom_Allegati : BaseDataClass
	{
		string codicetestata=null;
		[DataField("CODICETESTATA", Type=DbType.Decimal)]
		public string CODICETESTATA
		{
			get { return codicetestata; }
			set { codicetestata = value; }
		}

		string allegato=null;
		[DataField("ALLEGATO",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ALLEGATO
		{
			get { return allegato; }
			set { allegato = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}