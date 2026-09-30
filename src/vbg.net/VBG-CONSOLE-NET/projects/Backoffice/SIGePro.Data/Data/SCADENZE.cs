using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SCADENZE")]
	public class Scadenze : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicescadenza=null;
		[KeyField("CODICESCADENZA", Type=DbType.Decimal)]
		public string CODICESCADENZA
		{
			get { return codicescadenza; }
			set { codicescadenza = value; }
		}

		string codiceanagrafe=null;
		[DataField("CODICEANAGRAFE", Type=DbType.Decimal)]
		public string CODICEANAGRAFE
		{
			get { return codiceanagrafe; }
			set { codiceanagrafe = value; }
		}

        DateTime? dataregistrazione = null;
		[DataField("DATAREGISTRAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAREGISTRAZIONE
		{
			get { return dataregistrazione; }
            set { dataregistrazione = VerificaDataLocale(value); }
		}

        DateTime? datascadenza = null;
		[DataField("DATASCADENZA", Type=DbType.DateTime)]
		public DateTime? DATASCADENZA
		{
			get { return datascadenza; }
            set { datascadenza = VerificaDataLocale(value); }
		}

		string scadenza=null;
		[DataField("SCADENZA",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SCADENZA
		{
			get { return scadenza; }
			set { scadenza = value; }
		}

		string note=null;
		[DataField("NOTE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}
	}
}