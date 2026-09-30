using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PROCEDIMENTIFO")]
	public class ProcedimentiFo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string titolo=null;
		[KeyField("TITOLO",Size=400, Type=DbType.String, Compare="like", CaseSensitive=false)]
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
	}
}