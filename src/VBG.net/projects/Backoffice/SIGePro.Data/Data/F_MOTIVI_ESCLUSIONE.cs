using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_MOTIVI_ESCLUSIONE")]
	public class F_Motivi_Esclusione : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string mt_id=null;
		[KeyField("MT_ID", Type=DbType.Decimal)]
		public string MT_ID
		{
			get { return mt_id; }
			set { mt_id = value; }
		}

		string mt_query=null;
		[DataField("MT_QUERY",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MT_QUERY
		{
			get { return mt_query; }
			set { mt_query = value; }
		}

		string mt_desc_esclusione=null;
		[DataField("MT_DESC_ESCLUSIONE",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MT_DESC_ESCLUSIONE
		{
			get { return mt_desc_esclusione; }
			set { mt_desc_esclusione = value; }
		}
	}
}