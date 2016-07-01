using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("I_ATTIVITAMERCATIPRESENZE")]
	public class I_AttivitaMercatiPresenze : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string fkidi_attivita=null;
		[DataField("FKIDI_ATTIVITA", Type=DbType.Decimal)]
		public string FKIDI_ATTIVITA
		{
			get { return fkidi_attivita; }
			set { fkidi_attivita = value; }
		}

		string fkcodicemercato=null;
		[DataField("FKCODICEMERCATO", Type=DbType.Decimal)]
		public string FKCODICEMERCATO
		{
			get { return fkcodicemercato; }
			set { fkcodicemercato = value; }
		}

		string fkidposteggio=null;
		[DataField("FKIDPOSTEGGIO", Type=DbType.Decimal)]
		public string FKIDPOSTEGGIO
		{
			get { return fkidposteggio; }
			set { fkidposteggio = value; }
		}

		string fkidmercatiuso=null;
		[DataField("FKIDMERCATIUSO", Type=DbType.Decimal)]
		public string FKIDMERCATIUSO
		{
			get { return fkidmercatiuso; }
			set { fkidmercatiuso = value; }
		}

		string presente=null;
		[DataField("PRESENTE", Type=DbType.Decimal)]
		public string PRESENTE
		{
			get { return presente; }
			set { presente = value; }
		}

		string datarilevazione=null;
		[DataField("DATARILEVAZIONE", Type=DbType.DateTime)]
		public string DATARILEVAZIONE
		{
			get { return datarilevazione; }
			set { datarilevazione = value; }
		}

		string numeropresenze=null;
		[DataField("NUMEROPRESENZE", Type=DbType.Decimal)]
		public string NUMEROPRESENZE
		{
			get { return numeropresenze; }
			set { numeropresenze = value; }
		}

	}
}