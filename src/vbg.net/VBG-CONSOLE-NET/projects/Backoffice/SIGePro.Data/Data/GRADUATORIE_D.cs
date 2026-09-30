using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("GRADUATORIE_D")]
	public class Graduatorie_D : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string fkidt=null;
		[DataField("FKIDT", Type=DbType.Decimal)]
		public string FKIDT
		{
			get { return fkidt; }
			set { fkidt = value; }
		}

		string id=null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
		public string ID
		{
			get { return id; }
			set { id = value; }
		}

		string ordine=null;
		[DataField("ORDINE", Type=DbType.Decimal)]
		public string ORDINE
		{
			get { return ordine; }
			set { ordine = value; }
		}

		string fkcodiceistanza=null;
		[DataField("FKCODICEISTANZA", Type=DbType.Decimal)]
		public string FKCODICEISTANZA
		{
			get { return fkcodiceistanza; }
			set { fkcodiceistanza = value; }
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

	}
}