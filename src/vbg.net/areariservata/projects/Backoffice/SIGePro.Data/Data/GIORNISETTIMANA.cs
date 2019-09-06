using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("GIORNISETTIMANA")]
	[Serializable]
	public class GiorniSettimana : BaseDataClass
	{
		
		#region Key Fields
			
		string gs_id=null;
		[KeyField("GS_ID", Type=DbType.Decimal, KeyIdentity=true)]
		public string GS_ID
		{
			get { return gs_id; }
			set { gs_id = value; }
		}

		#endregion

		string gs_descrizione=null;
		[DataField("GS_DESCRIZIONE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string GS_DESCRIZIONE
		{
			get { return gs_descrizione; }
			set { gs_descrizione = value; }
		}

		string gs_valore=null;
		[DataField("GS_VALORE",Type=DbType.Decimal)]
		public string GS_VALORE
		{
			get { return gs_valore; }
			set { gs_valore = value; }
		}

	}
}