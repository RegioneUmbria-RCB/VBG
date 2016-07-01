using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("SEQUENCETABLE")]
	public class SequenceTable : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string sequencename=null;
		[KeyField("SEQUENCENAME",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SEQUENCENAME
		{
			get { return sequencename; }
			set { sequencename = value; }
		}

		string currval=null;
		[DataField("CURRVAL", Type=DbType.Decimal)]
		public string CURRVAL
		{
			get { return currval; }
			set { currval = value; }
		}

	}
}