using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FIDEJUSSIONESTATI")]
	public class FidejussioneStati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicestato=null;
		[KeyField("CODICESTATO", Type=DbType.Decimal)]
		public string CODICESTATO
		{
			get { return codicestato; }
			set { codicestato = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
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