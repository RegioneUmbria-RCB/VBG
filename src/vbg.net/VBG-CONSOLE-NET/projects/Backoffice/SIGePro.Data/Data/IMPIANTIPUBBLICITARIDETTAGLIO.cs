using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("IMPIANTIPUBBLICITARIDETTAGLIO")]
	public class ImpiantiPubblicitariDettaglio : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string iddettaglio=null;
		[KeyField("IDDETTAGLIO", Type=DbType.Decimal)]
		public string IDDETTAGLIO
		{
			get { return iddettaglio; }
			set { iddettaglio = value; }
		}

		string fk_codiceimpianto=null;
		[KeyField("FK_CODICEIMPIANTO", Type=DbType.Decimal)]
		public string FK_CODICEIMPIANTO
		{
			get { return fk_codiceimpianto; }
			set { fk_codiceimpianto = value; }
		}

		string fk_tipoaffissione=null;
		[DataField("FK_TIPOAFFISSIONE", Type=DbType.Decimal)]
		public string FK_TIPOAFFISSIONE
		{
			get { return fk_tipoaffissione; }
			set { fk_tipoaffissione = value; }
		}

		string fk_formato=null;
		[DataField("FK_FORMATO", Type=DbType.Decimal)]
		public string FK_FORMATO
		{
			get { return fk_formato; }
			set { fk_formato = value; }
		}

		string numero=null;
		[DataField("NUMERO", Type=DbType.Decimal)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}
	}
}