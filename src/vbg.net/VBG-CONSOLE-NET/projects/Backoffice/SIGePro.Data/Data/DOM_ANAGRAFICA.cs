using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("DOM_ANAGRAFICA")]
	public class Dom_Anagrafica : BaseDataClass
	{
		string codice=null;
		[DataField("CODICE", Type=DbType.Decimal)]
		public string CODICE
		{
			get { return codice; }
			set { codice = value; }
		}

		string codicefiscale=null;
		[DataField("CODICEFISCALE",Size=16, Type=DbType.String, CaseSensitive=false)]
		public string CODICEFISCALE
		{
			get { return codicefiscale; }
			set { codicefiscale = value; }
		}

		string password=null;
		[DataField("PASSWORD",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string PASSWORD
		{
			get { return password; }
			set { password = value; }
		}

		string nominativo=null;
		[DataField("NOMINATIVO",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMINATIVO
		{
			get { return nominativo; }
			set { nominativo = value; }
		}

		string codiceformagiuridica=null;
		[DataField("CODICEFORMAGIURIDICA", Type=DbType.Decimal)]
		public string CODICEFORMAGIURIDICA
		{
			get { return codiceformagiuridica; }
			set { codiceformagiuridica = value; }
		}

		string residenzaindirizzo=null;
		[DataField("RESIDENZAINDIRIZZO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RESIDENZAINDIRIZZO
		{
			get { return residenzaindirizzo; }
			set { residenzaindirizzo = value; }
		}

		string residenzalocalita=null;
		[DataField("RESIDENZALOCALITA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RESIDENZALOCALITA
		{
			get { return residenzalocalita; }
			set { residenzalocalita = value; }
		}

		string residenzacap=null;
		[DataField("RESIDENZACAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string RESIDENZACAP
		{
			get { return residenzacap; }
			set { residenzacap = value; }
		}

		string residenzacomune=null;
		[DataField("RESIDENZACOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string RESIDENZACOMUNE
		{
			get { return residenzacomune; }
			set { residenzacomune = value; }
		}

		string residenzaprovincia=null;
		[DataField("RESIDENZAPROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string RESIDENZAPROVINCIA
		{
			get { return residenzaprovincia; }
			set { residenzaprovincia = value; }
		}

		string corrispondenzaindirizzo=null;
		[DataField("CORRISPONDENZAINDIRIZZO",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CORRISPONDENZAINDIRIZZO
		{
			get { return corrispondenzaindirizzo; }
			set { corrispondenzaindirizzo = value; }
		}

		string corrispondenzalocalita=null;
		[DataField("CORRISPONDENZALOCALITA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CORRISPONDENZALOCALITA
		{
			get { return corrispondenzalocalita; }
			set { corrispondenzalocalita = value; }
		}

		string corrispondenzacap=null;
		[DataField("CORRISPONDENZACAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CORRISPONDENZACAP
		{
			get { return corrispondenzacap; }
			set { corrispondenzacap = value; }
		}

		string corrispondenzacomune=null;
		[DataField("CORRISPONDENZACOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CORRISPONDENZACOMUNE
		{
			get { return corrispondenzacomune; }
			set { corrispondenzacomune = value; }
		}

		string corrispondenzaprovincia=null;
		[DataField("CORRISPONDENZAPROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string CORRISPONDENZAPROVINCIA
		{
			get { return corrispondenzaprovincia; }
			set { corrispondenzaprovincia = value; }
		}

        DateTime? datacostituzione = null;
		[DataField("DATACOSTITUZIONE", Type=DbType.DateTime)]
		public DateTime? DATACOSTITUZIONE
		{
			get { return datacostituzione; }
            set { datacostituzione = VerificaDataLocale(value); }
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

		string partitaiva=null;
		[DataField("PARTITAIVA",Size=11, Type=DbType.String, CaseSensitive=false)]
		public string PARTITAIVA
		{
			get { return partitaiva; }
			set { partitaiva = value; }
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
		[DataField("LRCOMUNENASCITA",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string LRCOMUNENASCITA
		{
			get { return lrcomunenascita; }
			set { lrcomunenascita = value; }
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

		string referente=null;
		[DataField("REFERENTE",Size=80, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string REFERENTE
		{
			get { return referente; }
			set { referente = value; }
		}

		string nome=null;
		[DataField("NOME",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOME
		{
			get { return nome; }
			set { nome = value; }
		}

		string titolo=null;
		[DataField("TITOLO", Type=DbType.Decimal)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string cittadinanza=null;
		[DataField("CITTADINANZA", Type=DbType.Decimal)]
		public string CITTADINANZA
		{
			get { return cittadinanza; }
			set { cittadinanza = value; }
		}

        DateTime? datanascita = null;
		[DataField("DATANASCITA", Type=DbType.DateTime)]
		public DateTime? DATANASCITA
		{
			get { return datanascita; }
            set { datanascita = VerificaDataLocale(value); }
		}

		string nascitacomune=null;
		[DataField("NASCITACOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string NASCITACOMUNE
		{
			get { return nascitacomune; }
			set { nascitacomune = value; }
		}

		string sesso=null;
		[DataField("SESSO",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string SESSO
		{
			get { return sesso; }
			set { sesso = value; }
		}

		string persona=null;
		[DataField("PERSONA",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string PERSONA
		{
			get { return persona; }
			set { persona = value; }
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