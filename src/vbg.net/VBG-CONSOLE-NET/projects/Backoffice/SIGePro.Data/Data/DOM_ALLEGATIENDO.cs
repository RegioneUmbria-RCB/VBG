using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_ALLEGATIENDO")]
	public class Dom_AllegatiEndo : BaseDataClass
	{
		string codiceinventario=null;
		[DataField("CODICEINVENTARIO", Type=DbType.Decimal)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string numeroallegato=null;
		[DataField("NUMEROALLEGATO", Type=DbType.Decimal)]
		public string NUMEROALLEGATO
		{
			get { return numeroallegato; }
			set { numeroallegato = value; }
		}

		string codicetestata=null;
		[DataField("CODICETESTATA", Type=DbType.Decimal)]
		public string CODICETESTATA
		{
			get { return codicetestata; }
			set { codicetestata = value; }
		}

		string allegatoextra=null;
		[DataField("ALLEGATOEXTRA",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ALLEGATOEXTRA
		{
			get { return allegatoextra; }
			set { allegatoextra = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string costopresunto=null;
		[DataField("COSTOPRESUNTO", Type=DbType.Decimal)]
		public string COSTOPRESUNTO
		{
			get { return costopresunto; }
			set { costopresunto = value; }
		}

		string idcomune=null;
		[DataField("IDCOMUNE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

	}
}