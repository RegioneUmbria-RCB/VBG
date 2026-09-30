using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CONCESSIONICAUSALI")]
	public class ConcessioniCausali : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecausale=null;
		[useSequence]
		[KeyField("CODICECAUSALE", Type=DbType.Decimal)]
		public string CODICECAUSALE
		{
			get { return codicecausale; }
			set { codicecausale = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=35, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string causalestorico=null;
		[DataField("CAUSALESTORICO", Type=DbType.Decimal)]
		public string CAUSALESTORICO
		{
			get { return causalestorico; }
			set { causalestorico = value; }
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