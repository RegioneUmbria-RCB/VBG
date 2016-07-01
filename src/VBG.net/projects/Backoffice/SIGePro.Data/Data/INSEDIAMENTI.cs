using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INSEDIAMENTI")]
	public class Insediamenti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinsediamento=null;
		[KeyField("CODICEINSEDIAMENTO", Type=DbType.Decimal)]
		public string CODICEINSEDIAMENTO
		{
			get { return codiceinsediamento; }
			set { codiceinsediamento = value; }
		}

		string insediamento=null;
		[DataField("INSEDIAMENTO",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INSEDIAMENTO
		{
			get { return insediamento; }
			set { insediamento = value; }
		}

		string codiceistat=null;
		[DataField("CODICEISTAT",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string CODICEISTAT
		{
			get { return codiceistat; }
			set { codiceistat = value; }
		}

		string codiceintervento=null;
		[DataField("CODICEINTERVENTO", Type=DbType.Decimal)]
		public string CODICEINTERVENTO
		{
			get { return codiceintervento; }
			set { codiceintervento = value; }
		}

		string note=null;
		[DataField("NOTE",Size=2000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string codicesettore=null;
		[DataField("CODICESETTORE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CODICESETTORE
		{
			get { return codicesettore; }
			set { codicesettore = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string codiceinterventoproc=null;
		[DataField("CODICEINTERVENTOPROC", Type=DbType.Decimal)]
		public string CODICEINTERVENTOPROC
		{
			get { return codiceinterventoproc; }
			set { codiceinterventoproc = value; }
		}
	}
}