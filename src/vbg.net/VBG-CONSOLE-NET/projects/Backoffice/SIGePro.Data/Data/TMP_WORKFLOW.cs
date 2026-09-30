using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_WORKFLOW")]
	public class Tmp_WorkFlow : BaseDataClass
	{
		string amministrazione=null;
		[DataField("AMMINISTRAZIONE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string procedimento=null;
		[DataField("PROCEDIMENTO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROCEDIMENTO
		{
			get { return procedimento; }
			set { procedimento = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string movimento=null;
		[DataField("MOVIMENTO",Size=128, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOVIMENTO
		{
			get { return movimento; }
			set { movimento = value; }
		}

		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

        DateTime? datastampa = null;
		[DataField("DATASTAMPA", Type=DbType.DateTime)]
		public DateTime? DATASTAMPA
		{
			get { return datastampa; }
            set { datastampa = VerificaDataLocale(value); }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string nominativo=null;
		[DataField("NOMINATIVO",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMINATIVO
		{
			get { return nominativo; }
			set { nominativo = value; }
		}

		string ufficio=null;
		[DataField("UFFICIO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string UFFICIO
		{
			get { return ufficio; }
			set { ufficio = value; }
		}

	}
}