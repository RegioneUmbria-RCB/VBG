using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIPROCEDUREAVVIO")]
	[Serializable]
	public class TipiProcedureAvvio : BaseDataClass
	{

		#region Key Fields

		string codiceprocedura=null;
		[KeyField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string defaultsn=null;
		[DataField("DEFAULTSN", Type=DbType.Decimal)]
		public string DEFAULTSN
		{
			get { return defaultsn; }
			set { defaultsn = value; }
		}

	}
}