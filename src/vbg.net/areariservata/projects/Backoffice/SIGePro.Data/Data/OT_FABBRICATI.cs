using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("OT_FABBRICATI")]
	public class Ot_Fabbricati : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string numogg=null;
		[KeyField("NUMOGG",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string NUMOGG
		{
			get { return numogg; }
			set { numogg = value; }
		}

		string comune=null;
		[DataField("COMUNE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string COMUNE
		{
			get { return comune; }
			set { comune = value; }
		}

		string comistat=null;
		[DataField("COMISTAT",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string COMISTAT
		{
			get { return comistat; }
			set { comistat = value; }
		}

		string prov=null;
		[DataField("PROV",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string PROV
		{
			get { return prov; }
			set { prov = value; }
		}

		string cap=null;
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=40, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string codvia=null;
		[DataField("CODVIA", Type=DbType.Decimal)]
		public string CODVIA
		{
			get { return codvia; }
			set { codvia = value; }
		}

		string sezione=null;
		[DataField("SEZIONE",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string SEZIONE
		{
			get { return sezione; }
			set { sezione = value; }
		}

		string foglio=null;
		[DataField("FOGLIO",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string FOGLIO
		{
			get { return foglio; }
			set { foglio = value; }
		}

		string annoacc=null;
		[DataField("ANNOACC",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string ANNOACC
		{
			get { return annoacc; }
			set { annoacc = value; }
		}

		string civico=null;
		[DataField("CIVICO",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CIVICO
		{
			get { return civico; }
			set { civico = value; }
		}

		string bis=null;
		[DataField("BIS",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string BIS
		{
			get { return bis; }
			set { bis = value; }
		}

		string interno=null;
		[DataField("INTERNO",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string INTERNO
		{
			get { return interno; }
			set { interno = value; }
		}

		string protocollo=null;
		[DataField("PROTOCOLLO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string PROTOCOLLO
		{
			get { return protocollo; }
			set { protocollo = value; }
		}

		string numero=null;
		[DataField("NUMERO",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

		string subalterno=null;
		[DataField("SUBALTERNO",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string SUBALTERNO
		{
			get { return subalterno; }
			set { subalterno = value; }
		}

		string indirizzoden=null;
		[DataField("INDIRIZZODEN",Size=40, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZODEN
		{
			get { return indirizzoden; }
			set { indirizzoden = value; }
		}

        DateTime? datacreaz = null;
		[DataField("DATACREAZ", Type=DbType.DateTime)]
		public DateTime? DATACREAZ
		{
			get { return datacreaz; }
            set { datacreaz = VerificaDataLocale(value); }
		}

		string piano=null;
		[DataField("PIANO",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string PIANO
		{
			get { return piano; }
			set { piano = value; }
		}

	}
}