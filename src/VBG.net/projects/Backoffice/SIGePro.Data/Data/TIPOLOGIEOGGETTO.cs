using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPOLOGIEOGGETTO")]
	public class TipologieOggetto : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicetipologia=null;
		[KeyField("CODICETIPOLOGIA", Type=DbType.Decimal)]
		public string CODICETIPOLOGIA
		{
			get { return codicetipologia; }
			set { codicetipologia = value; }
		}

		string descrizionetipologia=null;
		[DataField("DESCRIZIONETIPOLOGIA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONETIPOLOGIA
		{
			get { return descrizionetipologia; }
			set { descrizionetipologia = value; }
		}
	}
}