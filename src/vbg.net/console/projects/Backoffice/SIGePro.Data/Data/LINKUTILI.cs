using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LINKUTILI")]
	public class LinkUtili : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codice=null;
		[KeyField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string link=null;
		[DataField("LINK",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LINK
		{
			get { return link; }
			set { link = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string note=null;
		[DataField("NOTE",Size=800, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}