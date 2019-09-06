using System;
using System.Data;
using Init.SIGeProExport.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
	[DataTable("QUERY")]
	[Serializable]
	public class CQUERY : BaseDataClass
	{
		#region Key Fields

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		#endregion

		string alias=null;
		[DataField("ALIAS",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ALIAS
		{
			get { return alias; }
			set { alias = value; }
		}

		string query=null;
		[DataField("QUERY",Size=250, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string QUERY
		{
			get { return query; }
			set { query = value; }
		}

	}
}