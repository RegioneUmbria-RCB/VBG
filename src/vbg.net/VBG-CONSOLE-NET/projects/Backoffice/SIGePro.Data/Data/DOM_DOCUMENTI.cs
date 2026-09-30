using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_DOCUMENTI")]
	public class Dom_Documenti : BaseDataClass
	{
		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string codicedomanda=null;
		[DataField("CODICEDOMANDA", Type=DbType.Decimal)]
		public string CODICEDOMANDA
		{
			get { return codicedomanda; }
			set { codicedomanda = value; }
		}

        DateTime? dataupload = null;
		[DataField("DATAUPLOAD", Type=DbType.DateTime)]
		public DateTime? DATAUPLOAD
		{
			get { return dataupload; }
            set { dataupload = VerificaDataLocale(value); }
		}

		string nomefile=null;
		[DataField("NOMEFILE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMEFILE
		{
			get { return nomefile; }
			set { nomefile = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
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