using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("VERSIONE")]
	public class Versione : BaseDataClass
	{
		string _versione=null;
		[DataField("VERSIONE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string versione
		{
			get { return _versione; }
			set { _versione = value; }
		}

	}
}