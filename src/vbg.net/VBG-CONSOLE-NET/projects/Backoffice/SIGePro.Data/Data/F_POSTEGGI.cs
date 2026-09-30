using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("F_POSTEGGI")]
	public class F_Posteggi : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string po_codice=null;
		[KeyField("PO_CODICE", Type=DbType.Decimal)]
		public string PO_CODICE
		{
			get { return po_codice; }
			set { po_codice = value; }
		}

		string po_seanimali=null;
		[DataField("PO_SEANIMALI", Type=DbType.Decimal)]
		public string PO_SEANIMALI
		{
			get { return po_seanimali; }
			set { po_seanimali = value; }
		}
	}
}