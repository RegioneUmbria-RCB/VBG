using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TEMPCONVOCAZIONI")]
	public class TempConvocazioni : BaseDataClass
	{
		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string tipodestinatario=null;
		[DataField("TIPODESTINATARIO", Type=DbType.Decimal)]
		public string TIPODESTINATARIO
		{
			get { return tipodestinatario; }
			set { tipodestinatario = value; }
		}

		string codicedestinatario=null;
		[DataField("CODICEDESTINATARIO", Type=DbType.Decimal)]
		public string CODICEDESTINATARIO
		{
			get { return codicedestinatario; }
			set { codicedestinatario = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=512, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string corpo=null;
		[DataField("CORPO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CORPO
		{
			get { return corpo; }
			set { corpo = value; }
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