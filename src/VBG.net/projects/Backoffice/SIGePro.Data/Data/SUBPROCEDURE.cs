using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SUBPROCEDURE")]
	public class SubProcedure : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceprocedura=null;
		[KeyField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string numerosubprocedura=null;
		[KeyField("NUMEROSUBPROCEDURA", Type=DbType.Decimal)]
		public string NUMEROSUBPROCEDURA
		{
			get { return numerosubprocedura; }
			set { numerosubprocedura = value; }
		}

		string titolosubprocedura=null;
		[DataField("TITOLOSUBPROCEDURA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLOSUBPROCEDURA
		{
			get { return titolosubprocedura; }
			set { titolosubprocedura = value; }
		}

		string subprocedura=null;
		[DataField("SUBPROCEDURA",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SUBPROCEDURA
		{
			get { return subprocedura; }
			set { subprocedura = value; }
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