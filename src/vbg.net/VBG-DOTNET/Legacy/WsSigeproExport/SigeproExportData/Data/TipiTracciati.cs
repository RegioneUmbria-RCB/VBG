using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
	[DataTable("TIPITRACCIATI")]
	[Serializable]
	public class TIPITRACCIATI : BaseDataClass
	{
		#region Key Fields

		string codicetipo=null;
		[KeyField("CODICETIPO",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string CODICETIPO
		{
			get { return codicetipo; }
			set { codicetipo = value; }
		}

		#endregion

		string tipo=null;
		[DataField("TIPO",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPO
		{
			get { return tipo; }
			set { tipo = value; }
		}

	}
}
