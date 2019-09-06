using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("INVENTARIOPROCEDIMENTIPEOPLE")]
	public class InventarioProcedimentiPeople : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceinventario=null;
		[KeyField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string cod_proc_people=null;
		[DataField("COD_PROC_PEOPLE",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string COD_PROC_PEOPLE
		{
			get { return cod_proc_people; }
			set { cod_proc_people = value; }
		}

	}
}