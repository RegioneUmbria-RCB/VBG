using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("QUESITI")]
	public class Quesiti : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicequesito=null;
		[KeyField("CODICEQUESITO", Type=DbType.Decimal)]
		public string CODICEQUESITO
		{
			get { return codicequesito; }
			set { codicequesito = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string nominativo=null;
		[DataField("NOMINATIVO",Size=120, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string NOMINATIVO
		{
			get { return nominativo; }
			set { nominativo = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=120, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string cap=null;
		[DataField("CAP",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string CAP
		{
			get { return cap; }
			set { cap = value; }
		}

		string citta=null;
		[DataField("CITTA",Size=100, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string CITTA
		{
			get { return citta; }
			set { citta = value; }
		}

		string provincia=null;
		[DataField("PROVINCIA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string PROVINCIA
		{
			get { return provincia; }
			set { provincia = value; }
		}

		string telefono=null;
		[DataField("TELEFONO",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TELEFONO
		{
			get { return telefono; }
			set { telefono = value; }
		}

		string fax=null;
		[DataField("FAX",Size=25, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string FAX
		{
			get { return fax; }
			set { fax = value; }
		}

		string email=null;
		[DataField("EMAIL",Size=120, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string EMAIL
		{
			get { return email; }
			set { email = value; }
		}

		string quesito=null;
		[DataField("QUESITO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string QUESITO
		{
			get { return quesito; }
			set { quesito = value; }
		}

		string risposta=null;
		[DataField("RISPOSTA",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RISPOSTA
		{
			get { return risposta; }
			set { risposta = value; }
		}

		string letto=null;
		[DataField("LETTO", Type=DbType.Decimal)]
		public string LETTO
		{
			get { return letto; }
			set { letto = value; }
		}

		string codicefaqclasse=null;
		[DataField("CODICEFAQCLASSE", Type=DbType.Decimal)]
		public string CODICEFAQCLASSE
		{
			get { return codicefaqclasse; }
			set { codicefaqclasse = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}