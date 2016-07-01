using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("OPERATORIPROFILI")]
	public class OperatoriProfili : BaseDataClass
	{
		string op_descrizione=null;
		[DataField("OP_DESCRIZIONE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OP_DESCRIZIONE
		{
			get { return op_descrizione; }
			set { op_descrizione = value; }
		}

		string op_id=null;
		[DataField("OP_ID", Type=DbType.Decimal)]
		public string OP_ID
		{
			get { return op_id; }
			set { op_id = value; }
		}

	}
}