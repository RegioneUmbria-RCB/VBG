using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("AMMINISTRAZIONIRESPONSABILI")]
	[Serializable]
	public class AmministrazioniResponsabili : BaseDataClass
	{
		
		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		#endregion

		string codiceamministrazione=null;
		[DataField("CODICEAMMINISTRAZIONE", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string codiceresponsabile=null;
		[DataField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}



	}
}