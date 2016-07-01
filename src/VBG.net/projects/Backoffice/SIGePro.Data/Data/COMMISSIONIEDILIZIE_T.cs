using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("COMMISSIONIEDILIZIE_T")]
	public class CommisioniEdilizie_T : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicecommissione=null;
		[KeyField("CODICECOMMISSIONE", Type=DbType.Decimal)]
		public string CODICECOMMISSIONE
		{
			get { return codicecommissione; }
			set { codicecommissione = value; }
		}

		DateTime? data;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string numprotocollo=null;
		[DataField("NUMPROTOCOLLO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string NUMPROTOCOLLO
		{
			get { return numprotocollo; }
			set { numprotocollo = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONE
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string note=null;
		[DataField("NOTE",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string flagaperta=null;
		[DataField("FLAGAPERTA", Type=DbType.Decimal)]
		public string FLAGAPERTA
		{
			get { return flagaperta; }
			set { flagaperta = value; }
		}

		string idconvocazione=null;
		[DataField("IDCONVOCAZIONE", Type=DbType.Decimal)]
		public string IDCONVOCAZIONE
		{
			get { return idconvocazione; }
			set { idconvocazione = value; }
		}

		string orainizio=null;
		[DataField("ORAINIZIO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string ORAINIZIO
		{
			get { return orainizio; }
			set { orainizio = value; }
		}

		string orafine=null;
		[DataField("ORAFINE",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string ORAFINE
		{
			get { return orafine; }
			set { orafine = value; }
		}

		string codcommtipologia=null;
		[DataField("CODCOMMTIPOLOGIA", Type=DbType.Decimal)]
		public string CODCOMMTIPOLOGIA
		{
			get { return codcommtipologia; }
			set { codcommtipologia = value; }
		}

	}
}