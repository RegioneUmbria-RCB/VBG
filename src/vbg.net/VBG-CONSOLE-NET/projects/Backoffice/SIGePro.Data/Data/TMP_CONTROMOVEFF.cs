using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_CONTROMOVEFF")]
	public class Tmp_ControMovEff : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string tipomovimentotx=null;
		[DataField("TIPOMOVIMENTOTX",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTOTX
		{
			get { return tipomovimentotx; }
			set { tipomovimentotx = value; }
		}

		string codicemovimentotx=null;
		[DataField("CODICEMOVIMENTOTX", Type=DbType.Decimal)]
		public string CODICEMOVIMENTOTX
		{
			get { return codicemovimentotx; }
			set { codicemovimentotx = value; }
		}

		string codiceamministrazionetx=null;
		[DataField("CODICEAMMINISTRAZIONETX", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONETX
		{
			get { return codiceamministrazionetx; }
			set { codiceamministrazionetx = value; }
		}

		string codiceinventariotx=null;
		[DataField("CODICEINVENTARIOTX", Type=DbType.Decimal)]
		public string CODICEINVENTARIOTX
		{
			get { return codiceinventariotx; }
			set { codiceinventariotx = value; }
		}

		string codammrichiedentetx=null;
		[DataField("CODAMMRICHIEDENTETX", Type=DbType.Decimal)]
		public string CODAMMRICHIEDENTETX
		{
			get { return codammrichiedentetx; }
			set { codammrichiedentetx = value; }
		}

        DateTime? datatx = null;
		[DataField("DATATX", Type=DbType.DateTime)]
		public DateTime? DATATX
		{
			get { return datatx; }
            set { datatx = VerificaDataLocale(value); }
		}

		string esitotx=null;
		[DataField("ESITOTX", Type=DbType.Decimal)]
		public string ESITOTX
		{
			get { return esitotx; }
			set { esitotx = value; }
		}

		string tipomovimentorx=null;
		[DataField("TIPOMOVIMENTORX",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string TIPOMOVIMENTORX
		{
			get { return tipomovimentorx; }
			set { tipomovimentorx = value; }
		}

		string flagbaserx=null;
		[DataField("FLAGBASERX", Type=DbType.Decimal)]
		public string FLAGBASERX
		{
			get { return flagbaserx; }
			set { flagbaserx = value; }
		}

		string codicemovimentorx=null;
		[DataField("CODICEMOVIMENTORX", Type=DbType.Decimal)]
		public string CODICEMOVIMENTORX
		{
			get { return codicemovimentorx; }
			set { codicemovimentorx = value; }
		}

		string codiceamministrazionerx=null;
		[DataField("CODICEAMMINISTRAZIONERX", Type=DbType.Decimal)]
		public string CODICEAMMINISTRAZIONERX
		{
			get { return codiceamministrazionerx; }
			set { codiceamministrazionerx = value; }
		}

		string codiceinventariorx=null;
		[DataField("CODICEINVENTARIORX", Type=DbType.Decimal)]
		public string CODICEINVENTARIORX
		{
			get { return codiceinventariorx; }
			set { codiceinventariorx = value; }
		}

		string codammrichiedenterx=null;
		[DataField("CODAMMRICHIEDENTERX", Type=DbType.Decimal)]
		public string CODAMMRICHIEDENTERX
		{
			get { return codammrichiedenterx; }
			set { codammrichiedenterx = value; }
		}

        DateTime? datarx = null;
		[DataField("DATARX", Type=DbType.DateTime)]
		public DateTime? DATARX
		{
			get { return datarx; }
            set { datarx = VerificaDataLocale(value); }
		}

		string esitorx=null;
		[DataField("ESITORX", Type=DbType.Decimal)]
		public string ESITORX
		{
			get { return esitorx; }
			set { esitorx = value; }
		}

		string attesarx=null;
		[DataField("ATTESARX", Type=DbType.Decimal)]
		public string ATTESARX
		{
			get { return attesarx; }
			set { attesarx = value; }
		}

		string calcolaattesadainizioistanza=null;
		[DataField("CALCOLAATTESADAINIZIOISTANZA", Type=DbType.Decimal)]
		public string CALCOLAATTESADAINIZIOISTANZA
		{
			get { return calcolaattesadainizioistanza; }
			set { calcolaattesadainizioistanza = value; }
		}

        DateTime? datascadenzarx = null;
		[DataField("DATASCADENZARX", Type=DbType.DateTime)]
		public DateTime? DATASCADENZARX
		{
			get { return datascadenzarx; }
            set { datascadenzarx = VerificaDataLocale(value); }
		}

		string movimentorx=null;
		[DataField("MOVIMENTORX",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOVIMENTORX
		{
			get { return movimentorx; }
			set { movimentorx = value; }
		}

		string movimentotx=null;
		[DataField("MOVIMENTOTX",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOVIMENTOTX
		{
			get { return movimentotx; }
			set { movimentotx = value; }
		}

	}
}