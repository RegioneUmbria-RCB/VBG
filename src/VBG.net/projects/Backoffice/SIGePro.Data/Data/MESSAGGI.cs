using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("MESSAGGI")]
	public class Messaggi : BaseDataClass
	{
		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string idforum=null;
		[DataField("IDFORUM", Type=DbType.Decimal)]
		public string IDFORUM
		{
			get { return idforum; }
			set { idforum = value; }
		}

		string rif_messaggio=null;
		[DataField("RIF_MESSAGGIO", Type=DbType.Decimal)]
		public string RIF_MESSAGGIO
		{
			get { return rif_messaggio; }
			set { rif_messaggio = value; }
		}

		string data=null;
		[DataField("DATA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string DATA
		{
			get { return data; }
			set { data = value; }
		}

		string autore=null;
		[DataField("AUTORE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string AUTORE
		{
			get { return autore; }
			set { autore = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
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

		string ha_risposte=null;
		[DataField("HA_RISPOSTE", Type=DbType.Decimal)]
		public string HA_RISPOSTE
		{
			get { return ha_risposte; }
			set { ha_risposte = value; }
		}

		string verificato=null;
		[DataField("VERIFICATO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string VERIFICATO
		{
			get { return verificato; }
			set { verificato = value; }
		}
	}
}