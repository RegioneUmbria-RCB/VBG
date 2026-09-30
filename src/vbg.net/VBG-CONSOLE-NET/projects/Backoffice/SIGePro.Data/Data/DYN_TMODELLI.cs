using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DYN_TMODELLI")]
	[Serializable]
	public class DynTModelli : BaseDataClass
	{

		#region Key Fields

		string idmodello=null;
		[useSequence]
		[KeyField("IDMODELLO", Type=DbType.Decimal)]
		public string IDMODELLO
		{
			get { return idmodello; }
			set { idmodello = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=130, Type=DbType.String, CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
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