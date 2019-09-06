using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPOLOGIAISTANZA")]
	[Serializable]
	public class TipologiaIstanza : BaseDataClass
	{

		#region Key Fields

		string ti_id=null;
		[useSequence]
		[KeyField("TI_ID", Type=DbType.Decimal)]
		public string TI_ID
		{
			get { return ti_id; }
			set { ti_id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string ti_descrizione=null;
		[DataField("TI_DESCRIZIONE",Size=80, Type=DbType.String, CaseSensitive=false)]
		public string TI_DESCRIZIONE
		{
			get { return ti_descrizione; }
			set { ti_descrizione = value; }
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