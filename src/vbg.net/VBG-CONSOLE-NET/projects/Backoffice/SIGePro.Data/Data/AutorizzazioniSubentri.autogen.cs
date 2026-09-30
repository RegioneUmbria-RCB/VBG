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
	[DataTable("AUTORIZZAZIONI_SUBENTRI")]
	[Serializable]
	public partial class AutorizzazioniSubentri : BaseDataClass, IClasseContestoModelloDinamico
	{
		#region Membri privati
		private string m_idcomune = null;
		private int? m_id = null;
		private string m_autoriznumero = null;
		private DateTime? m_autorizdata = null;
		private string m_autorizcomune = null;
		private int? m_fkidregistro = null;
		private DateTime? m_autorizdataregistr = null;
		private string m_autorizresponsabile = null;
		private int? m_fkidistanza = null;
		private int? m_codicemovimento = null;
		private int? m_fkcodiceanagrafe = null;
		private int? m_fkcausaleacquisizione = null;
		private int? m_fkcausalecessazione = null;
		private DateTime? m_datacessazione = null;
		private int? m_fkidautattuale = null;
		private int? m_fkidautsubautcoll = null;
		#endregion

		#region properties

		#region Key Fields
		[useSequence]
		[KeyField("ID", Type = DbType.Decimal)]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}
		#endregion

		#region Data Fields
		[isRequired]
		[DataField("AUTORIZNUMERO", Size = 50, Type = DbType.String, CaseSensitive = false)]
		public string AutorizNumero
		{
			get { return m_autoriznumero; }
			set { m_autoriznumero = value; }
		}

		[DataField("AUTORIZDATA", Type = DbType.DateTime)]
		public DateTime? AutorizData
		{
			get { return m_autorizdata; }
			set { m_autorizdata = VerificaDataLocale(value); }
		}

		[isRequired]
		[DataField("AUTORIZCOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
		public string AutorizComune
		{
			get { return m_autorizcomune; }
			set { m_autorizcomune = value; }
		}

		[isRequired]
		[DataField("FKIDREGISTRO", Type = DbType.Decimal)]
		public int? FkIdRegistro
		{
			get { return m_fkidregistro; }
			set { m_fkidregistro = value; }
		}

		[DataField("AUTORIZDATAREGISTR", Type = DbType.DateTime)]
		public DateTime? AutorizDataRegistr
		{
			get { return m_autorizdataregistr; }
			set { m_autorizdataregistr = VerificaDataLocale(value); }
		}

		[DataField("AUTORIZRESPONSABILE", Size = 5, Type = DbType.String, CaseSensitive = false)]
		public string AutorizResponsabile
		{
			get { return m_autorizresponsabile; }
			set { m_autorizresponsabile = value; }
		}

		[DataField("FKIDISTANZA", Type = DbType.Decimal)]
		public int? FkIdIstanza
		{
			get { return m_fkidistanza; }
			set { m_fkidistanza = value; }
		}

		[DataField("CODICEMOVIMENTO", Type = DbType.Decimal)]
		public int? CodiceMovimento
		{
			get { return m_fkidistanza; }
			set { m_fkidistanza = value; }
		}

		[DataField("FK_CODICEANAGRAFE", Type = DbType.Decimal)]
		public int? FkCodiceAnagrafe
		{
			get { return m_fkcodiceanagrafe; }
			set { m_fkcodiceanagrafe = value; }
		}

		[DataField("FK_CAUSALE_ACQUISIZIONE", Type = DbType.Decimal)]
		public int? FkCausaleAcquisizione
		{
			get { return m_fkcausaleacquisizione; }
			set { m_fkcausaleacquisizione = value; }
		}

		[DataField("FK_CAUSALE_CESSAZIONE", Type = DbType.Decimal)]
		public int? FkCausaleCessazione
		{
			get { return m_fkcausalecessazione; }
			set { m_fkcausalecessazione = value; }
		}

		[DataField("DATA_CESSAZIONE", Type = DbType.DateTime)]
		public DateTime? DataCessazione
		{
			get { return m_datacessazione; }
			set { m_datacessazione = VerificaDataLocale(value); }
		}

		[isRequired]
		[DataField("FK_IDAUT_ATTUALE", Type = DbType.Decimal)]
		public int? FkIdAutAttuale
		{
			get { return m_fkidautattuale; }
			set { m_fkidautattuale = value; }
		}

		[DataField("FK_IDAUTSUB_AUTCOLL", Type = DbType.Decimal)]
		public int? FkIdAutSubAutColl
		{
			get { return m_fkidautsubautcoll; }
			set { m_fkidautsubautcoll = value; }
		}
		#endregion

		#endregion
	}
}
