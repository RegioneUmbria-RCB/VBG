
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
	///
	/// File generato automaticamente dalla tabella MOVIMENTIMAIL il 06/07/2010 10.52.29
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
	[DataTable("MOVIMENTIMAIL")]
	[Serializable]
	public partial class MovimentiMail : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private int? m_id = null;

		private int? m_codicemovimento = null;

		private string m_mittente = null;

		private string m_destinatario = null;

		private string m_destinatariocc = null;

		private string m_destinatariobcc = null;

		private string m_oggetto = null;

		private string m_corpo = null;

		private DateTime? m_datainvio = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		#endregion

		#region Data fields

		[DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
		public int? Codicemovimento
		{
			get { return m_codicemovimento; }
			set { m_codicemovimento = value; }
		}

		[DataField("MITTENTE", Type = DbType.String, CaseSensitive = false, Size = 50)]
		public string Mittente
		{
			get { return m_mittente; }
			set { m_mittente = value; }
		}

		[DataField("DESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 200)]
		public string Destinatario
		{
			get { return m_destinatario; }
			set { m_destinatario = value; }
		}

		[DataField("DESTINATARIOCC", Type = DbType.String, CaseSensitive = false, Size = 200)]
		public string Destinatariocc
		{
			get { return m_destinatariocc; }
			set { m_destinatariocc = value; }
		}

		[DataField("DESTINATARIOBCC", Type = DbType.String, CaseSensitive = false, Size = 200)]
		public string Destinatariobcc
		{
			get { return m_destinatariobcc; }
			set { m_destinatariobcc = value; }
		}

		[DataField("OGGETTO", Type = DbType.String, CaseSensitive = false, Size = 1000)]
		public string Oggetto
		{
			get { return m_oggetto; }
			set { m_oggetto = value; }
		}

		[DataField("CORPO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string Corpo
		{
			get { return m_corpo; }
			set { m_corpo = value; }
		}

		[DataField("DATAINVIO", Type = DbType.DateTime)]
		public DateTime? Datainvio
		{
			get { return m_datainvio; }
			set { m_datainvio = value; }
		}

		#endregion

		#endregion
	}
}
