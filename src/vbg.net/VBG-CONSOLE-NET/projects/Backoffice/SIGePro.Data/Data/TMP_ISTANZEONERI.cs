using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_ISTANZEONERI")]
	public class Tmp_IstanzeOneri : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string data=null;
		[DataField("DATA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string DATA
		{
			get { return data; }
			set { data = value; }
		}

		string endo=null;
		[DataField("ENDO",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ENDO
		{
			get { return endo; }
			set { endo = value; }
		}

		string entrata=null;
		[DataField("ENTRATA", Type=DbType.Decimal)]
		public string ENTRATA
		{
			get { return entrata; }
			set { entrata = value; }
		}

		string uscita=null;
		[DataField("USCITA", Type=DbType.Decimal)]
		public string USCITA
		{
			get { return uscita; }
			set { uscita = value; }
		}

		string uscitarib=null;
		[DataField("USCITARIB", Type=DbType.Decimal)]
		public string USCITARIB
		{
			get { return uscitarib; }
			set { uscitarib = value; }
		}

		string perc=null;
		[DataField("PERC", Type=DbType.Decimal)]
		public string PERC
		{
			get { return perc; }
			set { perc = value; }
		}

		string codistanza=null;
		[DataField("CODISTANZA", Type=DbType.Decimal)]
		public string CODISTANZA
		{
			get { return codistanza; }
			set { codistanza = value; }
		}

		string richiedente=null;
		[DataField("RICHIEDENTE",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RICHIEDENTE
		{
			get { return richiedente; }
			set { richiedente = value; }
		}

		string datapagamento=null;
		[DataField("DATAPAGAMENTO",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string DATAPAGAMENTO
		{
			get { return datapagamento; }
			set { datapagamento = value; }
		}

		string amministrazione=null;
		[DataField("AMMINISTRAZIONE",Size=200, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AMMINISTRAZIONE
		{
			get { return amministrazione; }
			set { amministrazione = value; }
		}

		string riferimento=null;
		[DataField("RIFERIMENTO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RIFERIMENTO
		{
			get { return riferimento; }
			set { riferimento = value; }
		}

		string prezzoistruttoria=null;
		[DataField("PREZZOISTRUTTORIA", Type=DbType.Decimal)]
		public string PREZZOISTRUTTORIA
		{
			get { return prezzoistruttoria; }
			set { prezzoistruttoria = value; }
		}

	}
}