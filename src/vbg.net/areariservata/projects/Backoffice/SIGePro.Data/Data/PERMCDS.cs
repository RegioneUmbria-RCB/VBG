using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("PERMCDS")]
	public class PermCdS : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecds=null;
		[KeyField("CODICECDS", Type=DbType.Decimal)]
		public string CODICECDS
		{
			get { return codicecds; }
			set { codicecds = value; }
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

		string odg=null;
		[DataField("ODG",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ODG
		{
			get { return odg; }
			set { odg = value; }
		}

		string note=null;
		[DataField("NOTE",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string invitorichiedente=null;
		[DataField("INVITORICHIEDENTE", Type=DbType.Decimal)]
		public string INVITORICHIEDENTE
		{
			get { return invitorichiedente; }
			set { invitorichiedente = value; }
		}
	}
}