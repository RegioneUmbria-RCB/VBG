using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIPROTOCOLLODOCTIPO")]
	public class TipiProtocolloDocTipo : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string tp_id=null;
		[KeyField("TP_ID", Type=DbType.Decimal)]
		public string TP_ID
		{
			get { return tp_id; }
			set { tp_id = value; }
		}

		string codicelettera=null;
		[KeyField("CODICELETTERA", Type=DbType.Decimal)]
		public string CODICELETTERA
		{
			get { return codicelettera; }
			set { codicelettera = value; }
		}
	}
}