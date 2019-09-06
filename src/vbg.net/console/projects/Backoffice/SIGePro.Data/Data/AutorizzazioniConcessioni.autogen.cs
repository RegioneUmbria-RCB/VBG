using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.SIGePro.Data
{
	///
	/// File generato automaticamente dalla tabella CAMPIGRADUATORIA il 01/04/2009 9.49.12
	///
	///												ATTENZIONE!!!
	///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
	/// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
	///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	[DataTable("AUTORIZZAZIONI_CONCESSIONI")]
	[Serializable]
	public partial class AutorizzazioniConcessioni : BaseDataClass, IClasseContestoModelloDinamico
	{
		#region Membri privati

		private string m_idcomune = null;
		private int? m_id = null;
		private int? m_fkcodicemercato = null;
		private int? m_fkidmercatiuso = null;
		private int? m_fkidposteggio = null;
		private string m_fktipoconcessione = null;
		private string m_stagionaleda = null;
		private string m_stagionalea = null;
		private DateTime? m_datascadenza = null;
		private int? m_fkidautattuale = null;
		private int? m_fkidautcollegata = null;
		#endregion

		#region properties

		#region Key Fields
		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[isRequired]
		[DataField("FK_CODICEMERCATO", Type = DbType.Decimal)]
		public int? FkCodiceMercato
		{
			get { return m_fkcodicemercato; }
			set { m_fkcodicemercato = value; }
		}

		[isRequired]
		[DataField("FK_IDMERCATIUSO", Type = DbType.Decimal)]
		public int? FkIdMercatiUso
		{
			get { return m_fkidmercatiuso; }
			set { m_fkidmercatiuso = value; }
		}

		[isRequired]
		[DataField("FK_IDPOSTEGGIO", Type = DbType.Decimal)]
		public int? FkIdPosteggio
		{
			get { return m_fkidposteggio; }
			set { m_fkidposteggio = value; }
		}

		[isRequired]
		[DataField("FK_TIPOCONCESSIONE", Type = DbType.String, CaseSensitive = false, Size = 2)]
		public string FkTipoConcessione
		{
			get { return m_fktipoconcessione; }
			set { m_fktipoconcessione = value; }
		}

		[DataField("STAGIONALEDA", Type = DbType.String, CaseSensitive = false, Size = 4)]
		public string StagionaleDa
		{
			get { return m_stagionaleda; }
			set { m_stagionaleda = value; }
		}

		[DataField("STAGIONALEA", Type = DbType.String, CaseSensitive = false, Size = 4)]
		public string StagionaleA
		{
			get { return m_stagionalea; }
			set { m_stagionalea = value; }
		}

		[DataField("DATASCADENZA", Type = DbType.DateTime)]
		public DateTime? DataScadenza
		{
			get { return m_datascadenza; }
			set { m_datascadenza = VerificaDataLocale(value); }
		}

		[isRequired]
		[DataField("FK_IDAUT_ATTUALE", Type = DbType.Decimal)]
		public int? FkIdAutAttuale
		{
			get { return m_fkidautattuale; }
			set { m_fkidautattuale = value; }
		}

		[DataField("FK_IDAUT_COLLEGATA", Type = DbType.Decimal)]
		public int? FkIdAutCollegata
		{
			get { return m_fkidautcollegata; }
			set { m_fkidautcollegata = value; }
		}

		#endregion
		#endregion
	}
}
