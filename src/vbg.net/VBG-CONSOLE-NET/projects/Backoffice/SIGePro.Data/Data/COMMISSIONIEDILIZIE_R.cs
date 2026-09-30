using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMISSIONIEDILIZIE_R")]
	public class CommissioniEdilizie_R : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
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

		string codicecommissione=null;
		[DataField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

		string codicemovimento=null;
		[DataField("CODICEMOVIMENTO", Type=DbType.Decimal)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}

		string ordine=null;
		[DataField("ORDINE", Type=DbType.Decimal)]
		public string ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

		string flagrinviata=null;
		[DataField("FLAGRINVIATA", Type=DbType.Decimal)]
		public string FLAGRINVIATA
		{
			get { return flagrinviata; }
			set { flagrinviata = value; }
		}

		string codicemovimentorientro=null;
		[DataField("CODICEMOVIMENTORIENTRO", Type=DbType.Decimal)]
		public string CODICEMOVIMENTORIENTRO
		{
			get { return codicemovimentorientro; }
			set { codicemovimentorientro = value; }
		}

		string tipoparere=null;
		[DataField("TIPOPARERE", Type=DbType.Decimal)]
		public string TIPOPARERE
		{
			get { return tipoparere; }
			set { tipoparere = value; }
		}

	}
}