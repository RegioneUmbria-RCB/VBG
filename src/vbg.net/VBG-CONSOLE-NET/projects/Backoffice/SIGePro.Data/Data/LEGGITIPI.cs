using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("LEGGITIPI")]
	public class LeggiTipi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string lt_id=null;
		[KeyField("LT_ID", Type=DbType.Decimal)]
		public string LT_ID
		{
			get { return lt_id; }
			set { lt_id = value; }
		}

		string lt_descrizione=null;
		[DataField("LT_DESCRIZIONE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string LT_DESCRIZIONE
		{
			get { return lt_descrizione; }
			set { lt_descrizione = value; }
		}
	}
}