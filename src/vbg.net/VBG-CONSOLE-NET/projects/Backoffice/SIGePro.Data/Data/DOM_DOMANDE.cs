using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_DOMANDE")]
	public class Dom_Domande : BaseDataClass
	{
		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string codiceutente=null;
		[DataField("CODICEUTENTE", Type=DbType.Decimal)]
		public string CODICEUTENTE
		{
			get { return codiceutente; }
			set { codiceutente = value; }
		}

        DateTime? datapresentazione = null;
		[DataField("DATAPRESENTAZIONE", Type=DbType.DateTime)]
		public DateTime? DATAPRESENTAZIONE
		{
			get { return datapresentazione; }
            set { datapresentazione = VerificaDataLocale(value); }
		}

		string codiceintervento=null;
		[DataField("CODICEINTERVENTO", Type=DbType.Decimal)]
		public string CODICEINTERVENTO
		{
			get { return codiceintervento; }
			set { codiceintervento = value; }
		}

		string codiceimpianto=null;
		[DataField("CODICEIMPIANTO", Type=DbType.Decimal)]
		public string CODICEIMPIANTO
		{
			get { return codiceimpianto; }
			set { codiceimpianto = value; }
		}

		string codiceprocedura=null;
		[DataField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string codicearea=null;
		[DataField("CODICEAREA", Type=DbType.Decimal)]
		public string CODICEAREA
		{
			get { return codicearea; }
			set { codicearea = value; }
		}

		string codicestradario=null;
		[DataField("CODICESTRADARIO", Type=DbType.Decimal)]
		public string CODICESTRADARIO
		{
			get { return codicestradario; }
			set { codicestradario = value; }
		}

		string lotto=null;
		[DataField("LOTTO", Type=DbType.Decimal)]
		public string LOTTO
		{
			get { return lotto; }
			set { lotto = value; }
		}

		string civico=null;
		[DataField("CIVICO",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CIVICO
		{
			get { return civico; }
			set { civico = value; }
		}

		string lavori=null;
		[DataField("LAVORI",Size=255, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LAVORI
		{
			get { return lavori; }
			set { lavori = value; }
		}

		string progetto=null;
		[DataField("PROGETTO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string PROGETTO
		{
			get { return progetto; }
			set { progetto = value; }
		}

		string stato=null;
		[DataField("STATO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string STATO
		{
			get { return stato; }
			set { stato = value; }
		}

		string motivo=null;
		[DataField("MOTIVO",Size=1000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string MOTIVO
		{
			get { return motivo; }
			set { motivo = value; }
		}

		string codicecomune=null;
		[DataField("CODICECOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CODICECOMUNE
		{
			get { return codicecomune; }
			set { codicecomune = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string codiceinterventoproc=null;
		[DataField("CODICEINTERVENTOPROC", Type=DbType.Decimal)]
		public string CODICEINTERVENTOPROC
		{
			get { return codiceinterventoproc; }
			set { codiceinterventoproc = value; }
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