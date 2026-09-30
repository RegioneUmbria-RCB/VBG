using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("RESPONSABILIRUOLI")]
	[Serializable]
	public class ResponsabiliRuoli : BaseDataClass
	{

		#region Key Fields

		string idruolo=null;
		[KeyField("IDRUOLO", Type=DbType.Decimal )]
		public string IDRUOLO
		{
			get { return idruolo; }
			set { idruolo = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceresponsabile=null;
		[KeyField("CODICERESPONSABILE", Type=DbType.Decimal )]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		#endregion
	}
}