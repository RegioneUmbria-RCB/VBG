using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PERMCDSATTI")]
	public class PermCdSAtti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceatto=null;
		[KeyField("CODICEATTO", Type=DbType.Decimal)]
		public string CODICEATTO
		{
			get { return codiceatto; }
			set { codiceatto = value; }
		}

		string codicecds=null;
		[KeyField("CODICECDS", Type=DbType.Decimal)]
		public string CODICECDS
		{
			get { return codicecds; }
			set { codicecds = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string ora=null;
		[DataField("ORA",Size=12, Type=DbType.String, CaseSensitive=false)]
		public string ORA
		{
			get { return ora; }
			set { ora = value; }
		}

		string verbale=null;
		[DataField("VERBALE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string VERBALE
		{
			get { return verbale; }
			set { verbale = value; }
		}

		string note=null;
		[DataField("NOTE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

        DateTime? dataconvocazione = null;
		[DataField("DATACONVOCAZIONE", Type=DbType.DateTime)]
		public DateTime? DATACONVOCAZIONE
		{
			get { return dataconvocazione; }
            set { dataconvocazione = VerificaDataLocale(value); }
		}

		string oraconvocazione=null;
		[DataField("ORACONVOCAZIONE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string ORACONVOCAZIONE
		{
			get { return oraconvocazione; }
			set { oraconvocazione = value; }
		}

        DateTime? dataconvocazione2 = null;
		[DataField("DATACONVOCAZIONE2", Type=DbType.DateTime)]
		public DateTime? DATACONVOCAZIONE2
		{
			get { return dataconvocazione2; }
            set { dataconvocazione2 = VerificaDataLocale(value); }
		}

		string oraconvocazione2=null;
		[DataField("ORACONVOCAZIONE2",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string ORACONVOCAZIONE2
		{
			get { return oraconvocazione2; }
			set { oraconvocazione2 = value; }
		}

		string chiusa=null;
		[DataField("CHIUSA",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string CHIUSA
		{
			get { return chiusa; }
			set { chiusa = value; }
		}

		string fileverbale=null;
		[DataField("FILEVERBALE",Size=70, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FILEVERBALE
		{
			get { return fileverbale; }
			set { fileverbale = value; }
		}

		string codiceoggetto=null;
		[DataField("CODICEOGGETTO", Type=DbType.Decimal)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}
	}
}