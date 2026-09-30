using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_REGISTROPROVVEDIMENTI")]
	public class Tmp_RegistroProvvedimenti : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string autoriznumero=null;
		[DataField("AUTORIZNUMERO",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AUTORIZNUMERO
		{
			get { return autoriznumero; }
			set { autoriznumero = value; }
		}

		string autorizdata=null;
		[DataField("AUTORIZDATA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string AUTORIZDATA
		{
			get { return autorizdata; }
			set { autorizdata = value; }
		}

		string autorizresponsabile=null;
		[DataField("AUTORIZRESPONSABILE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AUTORIZRESPONSABILE
		{
			get { return autorizresponsabile; }
			set { autorizresponsabile = value; }
		}

		string intervento=null;
		[DataField("INTERVENTO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INTERVENTO
		{
			get { return intervento; }
			set { intervento = value; }
		}

		string nominativo=null;
		[DataField("NOMINATIVO",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMINATIVO
		{
			get { return nominativo; }
			set { nominativo = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string decorrenza=null;
		[DataField("DECORRENZA",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DECORRENZA
		{
			get { return decorrenza; }
			set { decorrenza = value; }
		}

		string mqtot=null;
		[DataField("MQTOT",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string MQTOT
		{
			get { return mqtot; }
			set { mqtot = value; }
		}

		string procedura=null;
		[DataField("PROCEDURA",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROCEDURA
		{
			get { return procedura; }
			set { procedura = value; }
		}

		string lavori=null;
		[DataField("LAVORI",Size=600, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LAVORI
		{
			get { return lavori; }
			set { lavori = value; }
		}

		string registro=null;
		[DataField("REGISTRO",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string REGISTRO
		{
			get { return registro; }
			set { registro = value; }
		}

		string numeroistanza=null;
		[DataField("NUMEROISTANZA",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string NUMEROISTANZA
		{
			get { return numeroistanza; }
			set { numeroistanza = value; }
		}

		string codiceistanza=null;
		[DataField("CODICEISTANZA", Type=DbType.Decimal)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
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