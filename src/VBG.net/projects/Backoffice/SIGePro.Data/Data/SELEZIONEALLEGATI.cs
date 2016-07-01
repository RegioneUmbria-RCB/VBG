using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SELEZIONEALLEGATI")]
	public class SelezioneAllegati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceselezioneallegato=null;
		[KeyField("CODICESELEZIONEALLEGATO", Type=DbType.Decimal)]
		public string CODICESELEZIONEALLEGATO
		{
			get { return codiceselezioneallegato; }
			set { codiceselezioneallegato = value; }
		}

		string categoria=null;
		[DataField("CATEGORIA",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CATEGORIA
		{
			get { return categoria; }
			set { categoria = value; }
		}

		string selezioneallegato=null;
		[DataField("SELEZIONEALLEGATO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SELEZIONEALLEGATO
		{
			get { return selezioneallegato; }
			set { selezioneallegato = value; }
		}

		string note=null;
		[DataField("NOTE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}