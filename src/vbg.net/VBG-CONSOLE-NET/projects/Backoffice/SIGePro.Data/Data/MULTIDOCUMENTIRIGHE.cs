using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MULTIDOCUMENTIRIGHE")]
	public class MultiDocoumentiRighe : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicemuldoc=null;
		[KeyField("CODICEMULDOC", Type=DbType.Decimal)]
		public string CODICEMULDOC
		{
			get { return codicemuldoc; }
			set { codicemuldoc = value; }
		}

		string codicemuldocdettaglio=null;
		[KeyField("CODICEMULDOCDETTAGLIO", Type=DbType.Decimal)]
		public string CODICEMULDOCDETTAGLIO
		{
			get { return codicemuldocdettaglio; }
			set { codicemuldocdettaglio = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}
	}
}