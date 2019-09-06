using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AREE2")]
	public class Aree2 : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicearea=null;
		[KeyField("CODICEAREA", Type=DbType.Decimal)]
		public string CODICEAREA
		{
			get { return codicearea; }
			set { codicearea = value; }
		}

		string denominazione=null;
		[DataField("DENOMINAZIONE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DENOMINAZIONE
		{
			get { return denominazione; }
			set { denominazione = value; }
		}

		string proprieta=null;
		[DataField("PROPRIETA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROPRIETA
		{
			get { return proprieta; }
			set { proprieta = value; }
		}

		string localita=null;
		[DataField("LOCALITA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LOCALITA
		{
			get { return localita; }
			set { localita = value; }
		}

		string note=null;
		[DataField("NOTE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
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