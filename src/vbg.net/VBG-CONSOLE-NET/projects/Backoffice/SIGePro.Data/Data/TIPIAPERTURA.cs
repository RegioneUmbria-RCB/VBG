using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIAPERTURA")]
	[Serializable]
	public class TipiApertura : BaseDataClass
	{

		#region Key Fields

		string ta_id=null;
		[useSequence]
		[KeyField("TA_ID", Type=DbType.Decimal)]
		public string TA_ID
		{
			get { return ta_id; }
			set { ta_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string ta_descrizione=null;
		[DataField("TA_DESCRIZIONE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string TA_DESCRIZIONE
		{
			get { return ta_descrizione; }
			set { ta_descrizione = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}