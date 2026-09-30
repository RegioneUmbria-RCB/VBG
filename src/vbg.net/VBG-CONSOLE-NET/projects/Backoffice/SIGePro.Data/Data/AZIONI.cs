using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AZIONI")]
	[Serializable]
	public class Azioni : BaseDataClass
	{
		
		#region Key Fields

		string az_id=null;
		[KeyField("AZ_ID", Type=DbType.Decimal)]
		public string AZ_ID
		{
			get { return az_id; }
			set { az_id = value; }
		}

		#endregion

		string az_descrizione=null;
		[DataField("AZ_DESCRIZIONE",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string AZ_DESCRIZIONE
		{
			get { return az_descrizione; }
			set { az_descrizione = value; }
		}

		string az_azione=null;
		[DataField("AZ_AZIONE",Size=1, Type=DbType.String)]
		public string AZ_AZIONE
		{
			get { return az_azione; }
			set { az_azione = value; }
		}

	}
}