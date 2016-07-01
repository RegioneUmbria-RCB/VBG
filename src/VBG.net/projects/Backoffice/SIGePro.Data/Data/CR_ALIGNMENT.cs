using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CR_ALIGNMENT")]
	public class Cr_Alignment : BaseDataClass
	{
		string cod=null;
		[KeyField("COD", Type=DbType.Decimal)]
		public string COD
		{
			get { return cod; }
			set { cod = value; }
		}

		string dsc=null;
		[DataField("DSC",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string DSC
		{
			get { return dsc; }
			set { dsc = value; }
		}

	}
}