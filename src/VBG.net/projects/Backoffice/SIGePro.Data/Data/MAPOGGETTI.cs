using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MAPOGGETTI")]
	public class MapOggetti : BaseDataClass
	{
		string nometabella=null;
		[KeyField("NOMETABELLA",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMETABELLA
		{
			get { return nometabella; }
			set { nometabella = value; }
		}

		string nomecampo=null;
		[KeyField("NOMECAMPO",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMECAMPO
		{
			get { return nomecampo; }
			set { nomecampo = value; }
		}

	}
}