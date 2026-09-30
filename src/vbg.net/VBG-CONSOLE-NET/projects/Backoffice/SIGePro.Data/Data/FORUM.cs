using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("FORUM")]
	public class Forum : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceforum=null;
		[KeyField("CODICEFORUM", Type=DbType.Decimal)]
		public string CODICEFORUM
		{
			get { return codiceforum; }
			set { codiceforum = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string moderato=null;
		[DataField("MODERATO", Type=DbType.Decimal)]
		public string MODERATO
		{
			get { return moderato; }
			set { moderato = value; }
		}
	}
}