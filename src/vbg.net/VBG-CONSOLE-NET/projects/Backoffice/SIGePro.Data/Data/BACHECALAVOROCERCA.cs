using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("BACHECALAVOROCERCA")]
	public class BachecaLavoroCerca : BaseDataClass
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

        DateTime? scadenza = null;
		[DataField("SCADENZA", Type=DbType.DateTime)]
		public DateTime? SCADENZA
		{
			get { return scadenza; }
            set { scadenza = VerificaDataLocale(value); }
		}

		string titolodistudio=null;
		[DataField("TITOLODISTUDIO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLODISTUDIO
		{
			get { return titolodistudio; }
			set { titolodistudio = value; }
		}

	}
}