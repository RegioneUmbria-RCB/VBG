using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TIPIAFFISSIONIFORMATI")]
	public class TipiAffissioniFormati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string fk_tipoaffissione=null;
		[KeyField("FK_TIPOAFFISSIONE", Type=DbType.Decimal)]
		public string FK_TIPOAFFISSIONE
		{
			get { return fk_tipoaffissione; }
			set { fk_tipoaffissione = value; }
		}

		string fk_formato=null;
		[KeyField("FK_FORMATO", Type=DbType.Decimal)]
		public string FK_FORMATO
		{
			get { return fk_formato; }
			set { fk_formato = value; }
		}

	}
}