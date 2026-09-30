using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("CR_TYPE")]
	public class Cr_Type : BaseDataClass
	{
		string type=null;
		[KeyField("TYPE",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string TYPE
		{
			get { return type; }
			set { type = value; }
		}

	}
}