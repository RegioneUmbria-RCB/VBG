using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_STAT_02")]
	public class Tmp_Stat_02 : BaseDataClass
	{
		string sessionid=null;
		[KeyField("SESSIONID",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string idpratica=null;
		[KeyField("IDPRATICA", Type=DbType.Decimal)]
		public string IDPRATICA
		{
			get { return idpratica; }
			set { idpratica = value; }
		}

		string codiceistanza=null;
		[KeyField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

        DateTime? dataefficacia = null;
		[DataField("DATAEFFICACIA", Type=DbType.DateTime)]
		public DateTime? DATAEFFICACIA
		{
			get { return dataefficacia; }
            set { dataefficacia = VerificaDataLocale(value); }
		}

		string ordinecollegamento=null;
		[DataField("ORDINECOLLEGAMENTO", Type=DbType.Decimal)]
		public string ORDINECOLLEGAMENTO
		{
			get { return ordinecollegamento; }
			set { ordinecollegamento = value; }
		}

	}
}