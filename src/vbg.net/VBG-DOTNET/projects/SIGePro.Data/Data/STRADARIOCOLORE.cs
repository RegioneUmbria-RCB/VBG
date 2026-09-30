using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("STRADARIOCOLORE")]
	[Serializable]
	public class StradarioColore : BaseDataClass
	{

		#region Key Fields

		string codicecolore=null;
		[KeyField("CODICECOLORE",Size=2, Type=DbType.String)]
		public string CODICECOLORE
		{
			get { return codicecolore; }
			set { codicecolore = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string colore=null;
		[DataField("COLORE",Size=30, Type=DbType.String, CaseSensitive=false)]
		public string COLORE
		{
			get { return colore; }
			set { colore = value; }
		}

	}
}