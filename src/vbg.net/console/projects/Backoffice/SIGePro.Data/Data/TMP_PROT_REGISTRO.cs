using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("TMP_PROT_REGISTRO")]
	public class Tmp_Prot_Registro : BaseDataClass
	{
		string sessionid=null;
		[DataField("SESSIONID",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string SESSIONID
		{
			get { return sessionid; }
			set { sessionid = value; }
		}

		string numero=null;
		[DataField("NUMERO",Size=7, Type=DbType.String, CaseSensitive=false)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

		string anno=null;
		[DataField("ANNO",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string ANNO
		{
			get { return anno; }
			set { anno = value; }
		}

		string data=null;
		[DataField("DATA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string DATA
		{
			get { return data; }
			set { data = value; }
		}

		string oggetto=null;
		[DataField("OGGETTO",Size=2000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OGGETTO
		{
			get { return oggetto; }
			set { oggetto = value; }
		}

		string mittente=null;
		[DataField("MITTENTE",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MITTENTE
		{
			get { return mittente; }
			set { mittente = value; }
		}

		string destinatario=null;
		[DataField("DESTINATARIO",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESTINATARIO
		{
			get { return destinatario; }
			set { destinatario = value; }
		}

		string modalita=null;
		[DataField("MODALITA",Size=20, Type=DbType.String, CaseSensitive=false)]
		public string MODALITA
		{
			get { return modalita; }
			set { modalita = value; }
		}

		string annullato=null;
		[DataField("ANNULLATO",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string ANNULLATO
		{
			get { return annullato; }
			set { annullato = value; }
		}

		string operatore=null;
		[DataField("OPERATORE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string OPERATORE
		{
			get { return operatore; }
			set { operatore = value; }
		}

		string tipologia=null;
		[DataField("TIPOLOGIA",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPOLOGIA
		{
			get { return tipologia; }
			set { tipologia = value; }
		}

		string percontodi=null;
		[DataField("PERCONTODI",Size=180, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PERCONTODI
		{
			get { return percontodi; }
			set { percontodi = value; }
		}

		string altridestinatari=null;
		[DataField("ALTRIDESTINATARI",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ALTRIDESTINATARI
		{
			get { return altridestinatari; }
			set { altridestinatari = value; }
		}

	}
}