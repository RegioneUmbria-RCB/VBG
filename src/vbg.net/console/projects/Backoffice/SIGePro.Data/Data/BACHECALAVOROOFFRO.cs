using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("BACHECALAVOROOFFRO")]
	public class BachecaLavoroOffro : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string codiceanagrafe=null;
		[DataField("CODICEANAGRAFE", Type=DbType.Decimal)]
		public string CODICEANAGRAFE
		{
			get { return codiceanagrafe; }
			set { codiceanagrafe = value; }
		}

		string annuncio=null;
		[DataField("ANNUNCIO",Size=500, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ANNUNCIO
		{
			get { return annuncio; }
			set { annuncio = value; }
		}

		string qualifica=null;
		[DataField("QUALIFICA",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string QUALIFICA
		{
			get { return qualifica; }
			set { qualifica = value; }
		}

		string titolodistudio=null;
		[DataField("TITOLODISTUDIO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLODISTUDIO
		{
			get { return titolodistudio; }
			set { titolodistudio = value; }
		}

		string contattare=null;
		[DataField("CONTATTARE",Size=150, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CONTATTARE
		{
			get { return contattare; }
			set { contattare = value; }
		}

		string sedelavorocodicecomune=null;
		[DataField("SEDELAVOROCODICECOMUNE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string SEDELAVOROCODICECOMUNE
		{
			get { return sedelavorocodicecomune; }
			set { sedelavorocodicecomune = value; }
		}

		string sedelavoroindirizzo=null;
		[DataField("SEDELAVOROINDIRIZZO",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string SEDELAVOROINDIRIZZO
		{
			get { return sedelavoroindirizzo; }
			set { sedelavoroindirizzo = value; }
		}

		string tipocontratto=null;
		[DataField("TIPOCONTRATTO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TIPOCONTRATTO
		{
			get { return tipocontratto; }
			set { tipocontratto = value; }
		}

        DateTime? scadenza = null;
		[DataField("SCADENZA", Type=DbType.DateTime)]
		public DateTime? SCADENZA
		{
			get { return scadenza; }
            set { scadenza = VerificaDataLocale(value); }
		}

	}
}