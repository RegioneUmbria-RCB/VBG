using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LOTTI")]
	public class Lotti : BaseDataClass
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

		string codicelotto=null;
		[KeyField("CODICELOTTO", Type=DbType.Decimal)]
		public string CODICELOTTO
		{
			get { return codicelotto; }
			set { codicelotto = value; }
		}

		string assegnato=null;
		[DataField("ASSEGNATO", Type=DbType.Decimal)]
		public string ASSEGNATO
		{
			get { return assegnato; }
			set { assegnato = value; }
		}

		string note=null;
		[DataField("NOTE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}