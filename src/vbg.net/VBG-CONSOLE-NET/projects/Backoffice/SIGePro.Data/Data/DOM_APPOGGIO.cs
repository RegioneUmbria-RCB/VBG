using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_APPOGGIO")]
	public class Dom_Appoggio : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codiceutente=null;
		[KeyField("CODICEUTENTE", Type=DbType.Decimal)]
		public string CODICEUTENTE
		{
			get { return codiceutente; }
			set { codiceutente = value; }
		}

		string ragionesociale=null;
		[DataField("RAGIONESOCIALE",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RAGIONESOCIALE
		{
			get { return ragionesociale; }
			set { ragionesociale = value; }
		}

		string utcognome=null;
		[DataField("UTCOGNOME",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string UTCOGNOME
		{
			get { return utcognome; }
			set { utcognome = value; }
		}

		string utsesso=null;
		[DataField("UTSESSO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string UTSESSO
		{
			get { return utsesso; }
			set { utsesso = value; }
		}

        DateTime? utdatanascita = null;
		[DataField("UTDATANASCITA", Type=DbType.DateTime)]
		public DateTime? UTDATANASCITA
		{
			get { return utdatanascita; }
            set { utdatanascita = VerificaDataLocale(value); }
		}

		string utcomunenascita=null;
		[DataField("UTCOMUNENASCITA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string UTCOMUNENASCITA
		{
			get { return utcomunenascita; }
			set { utcomunenascita = value; }
		}

		string utcodicefiscale=null;
		[DataField("UTCODICEFISCALE",Size=16, Type=DbType.String, CaseSensitive=false)]
		public string UTCODICEFISCALE
		{
			get { return utcodicefiscale; }
			set { utcodicefiscale = value; }
		}

		string formagiuridica=null;
		[DataField("FORMAGIURIDICA", Type=DbType.Decimal)]
		public string FORMAGIURIDICA
		{
			get { return formagiuridica; }
			set { formagiuridica = value; }
		}

		string partitaiva=null;
		[DataField("PARTITAIVA",Size=16, Type=DbType.String, CaseSensitive=false)]
		public string PARTITAIVA
		{
			get { return partitaiva; }
			set { partitaiva = value; }
		}

		string legalerappresentante=null;
		[DataField("LEGALERAPPRESENTANTE",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LEGALERAPPRESENTANTE
		{
			get { return legalerappresentante; }
			set { legalerappresentante = value; }
		}

        DateTime? lrdatanascita = null;
		[DataField("LRDATANASCITA", Type=DbType.DateTime)]
		public DateTime? LRDATANASCITA
		{
			get { return lrdatanascita; }
            set { lrdatanascita = VerificaDataLocale(value); }
		}

		string lrcomunenascita=null;
		[DataField("LRCOMUNENASCITA",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LRCOMUNENASCITA
		{
			get { return lrcomunenascita; }
			set { lrcomunenascita = value; }
		}

		string referente=null;
		[DataField("REFERENTE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string REFERENTE
		{
			get { return referente; }
			set { referente = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string citta=null;
		[DataField("CITTA",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CITTA
		{
			get { return citta; }
			set { citta = value; }
		}

		string cap=null;
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string provincia=null;
		[DataField("PROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string PROVINCIA
		{
			get { return provincia; }
			set { provincia = value; }
		}

		string telefono=null;
		[DataField("TELEFONO",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string TELEFONO
		{
			get { return telefono; }
			set { telefono = value; }
		}

		string telefonocellulare=null;
		[DataField("TELEFONOCELLULARE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string TELEFONOCELLULARE
		{
			get { return telefonocellulare; }
			set { telefonocellulare = value; }
		}

		string fax=null;
		[DataField("FAX",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string FAX
		{
			get { return fax; }
			set { fax = value; }
		}

		string email=null;
		[DataField("EMAIL",Size=70, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string EMAIL
		{
			get { return email; }
			set { email = value; }
		}

		string regditte=null;
		[DataField("REGDITTE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string REGDITTE
		{
			get { return regditte; }
			set { regditte = value; }
		}

        DateTime? regdittedata = null;
		[DataField("REGDITTEDATA", Type=DbType.DateTime)]
		public DateTime? REGDITTEDATA
		{
			get { return regdittedata; }
            set { regdittedata = VerificaDataLocale(value); }
		}

		string regdittecom=null;
		[DataField("REGDITTECOM",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string REGDITTECOM
		{
			get { return regdittecom; }
			set { regdittecom = value; }
		}

		string regtrib=null;
		[DataField("REGTRIB",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string REGTRIB
		{
			get { return regtrib; }
			set { regtrib = value; }
		}

        DateTime? regtribdata = null;
		[DataField("REGTRIBDATA", Type=DbType.DateTime)]
		public DateTime? REGTRIBDATA
		{
			get { return regtribdata; }
            set { regtribdata = VerificaDataLocale(value); }
		}

		string regtribcom=null;
		[DataField("REGTRIBCOM",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string REGTRIBCOM
		{
			get { return regtribcom; }
			set { regtribcom = value; }
		}

		string codiceintervento=null;
		[DataField("CODICEINTERVENTO", Type=DbType.Decimal)]
		public string CODICEINTERVENTO
		{
			get { return codiceintervento; }
			set { codiceintervento = value; }
		}

		string codiceprocedura=null;
		[DataField("CODICEPROCEDURA", Type=DbType.Decimal)]
		public string CODICEPROCEDURA
		{
			get { return codiceprocedura; }
			set { codiceprocedura = value; }
		}

		string caplav=null;
		[DataField("CAPLAV",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CAPLAV
		{
			get { return caplav; }
			set { caplav = value; }
		}

		string indirizzolav=null;
		[DataField("INDIRIZZOLAV",Size=60, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZOLAV
		{
			get { return indirizzolav; }
			set { indirizzolav = value; }
		}

		string foglio=null;
		[DataField("FOGLIO",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string FOGLIO
		{
			get { return foglio; }
			set { foglio = value; }
		}

		string particella=null;
		[DataField("PARTICELLA",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string PARTICELLA
		{
			get { return particella; }
			set { particella = value; }
		}

		string sub=null;
		[DataField("SUB",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string SUB
		{
			get { return sub; }
			set { sub = value; }
		}

		string descrizionelavori=null;
		[DataField("DESCRIZIONELAVORI",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string DESCRIZIONELAVORI
		{
			get { return descrizionelavori; }
			set { descrizionelavori = value; }
		}

		string password=null;
		[DataField("PASSWORD",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string PASSWORD
		{
			get { return password; }
			set { password = value; }
		}

		string codicearea=null;
		[DataField("CODICEAREA", Type=DbType.Decimal)]
		public string CODICEAREA
		{
			get { return codicearea; }
			set { codicearea = value; }
		}

		string codicelotto=null;
		[DataField("CODICELOTTO", Type=DbType.Decimal)]
		public string CODICELOTTO
		{
			get { return codicelotto; }
			set { codicelotto = value; }
		}

		string codiceimpianto=null;
		[DataField("CODICEIMPIANTO", Type=DbType.Decimal)]
		public string CODICEIMPIANTO
		{
			get { return codiceimpianto; }
			set { codiceimpianto = value; }
		}

        DateTime? datacostituzione = null;
		[DataField("DATACOSTITUZIONE", Type=DbType.DateTime)]
		public DateTime? DATACOSTITUZIONE
		{
			get { return datacostituzione; }
            set { datacostituzione = VerificaDataLocale(value); }
		}

		string lrindirizzo=null;
		[DataField("LRINDIRIZZO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LRINDIRIZZO
		{
			get { return lrindirizzo; }
			set { lrindirizzo = value; }
		}

		string lrcitta=null;
		[DataField("LRCITTA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LRCITTA
		{
			get { return lrcitta; }
			set { lrcitta = value; }
		}

		string lrcap=null;
		[DataField("LRCAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string LRCAP
		{
			get { return lrcap; }
			set { lrcap = value; }
		}

		string lrprovincia=null;
		[DataField("LRPROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string LRPROVINCIA
		{
			get { return lrprovincia; }
			set { lrprovincia = value; }
		}

		string lrtelefono=null;
		[DataField("LRTELEFONO",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string LRTELEFONO
		{
			get { return lrtelefono; }
			set { lrtelefono = value; }
		}

		string lrtelefonocellulare=null;
		[DataField("LRTELEFONOCELLULARE",Size=15, Type=DbType.String, CaseSensitive=false)]
		public string LRTELEFONOCELLULARE
		{
			get { return lrtelefonocellulare; }
			set { lrtelefonocellulare = value; }
		}

		string codicecittadinanza=null;
		[DataField("CODICECITTADINANZA", Type=DbType.Decimal)]
		public string CODICECITTADINANZA
		{
			get { return codicecittadinanza; }
			set { codicecittadinanza = value; }
		}

		string comuneresidenza=null;
		[DataField("COMUNERESIDENZA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string COMUNERESIDENZA
		{
			get { return comuneresidenza; }
			set { comuneresidenza = value; }
		}

		string titolo=null;
		[DataField("TITOLO", Type=DbType.Decimal)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string lavoriestesa=null;
		[DataField("LAVORIESTESA",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string LAVORIESTESA
		{
			get { return lavoriestesa; }
			set { lavoriestesa = value; }
		}
	}
}