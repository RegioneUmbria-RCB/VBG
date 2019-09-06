using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("RESPONSABILISOFTWARE")]
	[Serializable]
	public class ResponsabiliSoftware : BaseDataClass
	{
	
		#region Key Fields

		string codiceresponsabile=null;
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		string software=null;
		[KeyField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

	}
}