using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INVENTARIOINSEDIAMENTI")]
	public class InventarioInsediamenti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinsediamento=null;
		[KeyField("CODICEINSEDIAMENTO", Type=DbType.Decimal)]
		public string CODICEINSEDIAMENTO
		{
			get { return codiceinsediamento; }
			set { codiceinsediamento = value; }
		}

		string codiceobbligatorio=null;
		[KeyField("CODICEOBBLIGATORIO", Type=DbType.Decimal)]
		public string CODICEOBBLIGATORIO
		{
			get { return codiceobbligatorio; }
			set { codiceobbligatorio = value; }
		}
	}
}