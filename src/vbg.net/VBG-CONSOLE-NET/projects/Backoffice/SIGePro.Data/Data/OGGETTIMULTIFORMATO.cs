using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("OGGETTIMULTIFORMATO")]
	public class OggettiMultiFormato : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceoggetto=null;
		[KeyField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string codiceoggettocollegato=null;
		[KeyField("CODICEOGGETTOCOLLEGATO", Type=DbType.Decimal)]
		public string CODICEOGGETTOCOLLEGATO
		{
			get { return codiceoggettocollegato; }
			set { codiceoggettocollegato = value; }
		}

	}
}