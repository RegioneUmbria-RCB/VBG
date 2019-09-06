
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
	/// File generato automaticamente dalla tabella FO_ARCONFIGURAZIONE il 11/04/2011 15.58.47
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
	[DataTable("FO_ARCONFIGURAZIONE")]
	[Serializable]
	public partial class FoArConfigurazione : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private string m_software = null;

		private string m_stato_iniziale_istanza = null;

		private string m_intestazione_dettaglio_visura = null;

		private string m_msg_invio_fallito = null;

		private int? m_codiceoggetto_firma = null;

		private int? m_codiceoggetto_sottoscriz = null;

		private string m_nome_parametro_login_url = null;

		private string m_msg_registrazione_completata = null;

		private string m_msg_invio_pec = null;

		private int? m_codiceoggetto_workflow = null;

		private int? m_codiceoggetto_menuxml = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[KeyField("SOFTWARE", Type = DbType.String, Size = 2)]
		public string Software
		{
			get { return m_software; }
			set { m_software = value; }
		}


		#endregion

		#region Data fields

		[DataField("STATO_INIZIALE_ISTANZA", Type = DbType.String, CaseSensitive = false, Size = 20)]
		public string StatoInizialeIstanza
		{
			get { return m_stato_iniziale_istanza; }
			set { m_stato_iniziale_istanza = value; }
		}

		[DataField("INTESTAZIONE_DETTAGLIO_VISURA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string IntestazioneDettaglioVisura
		{
			get { return m_intestazione_dettaglio_visura; }
			set { m_intestazione_dettaglio_visura = value; }
		}

		[DataField("MSG_INVIO_FALLITO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string MsgInvioFallito
		{
			get { return m_msg_invio_fallito; }
			set { m_msg_invio_fallito = value; }
		}

		[DataField("CODICEOGGETTO_FIRMA", Type = DbType.Decimal)]
		public int? CodiceoggettoFirma
		{
			get { return m_codiceoggetto_firma; }
			set { m_codiceoggetto_firma = value; }
		}

		[DataField("CODICEOGGETTO_SOTTOSCRIZ", Type = DbType.Decimal)]
		public int? CodiceoggettoSottoscriz
		{
			get { return m_codiceoggetto_sottoscriz; }
			set { m_codiceoggetto_sottoscriz = value; }
		}

		[DataField("NOME_PARAMETRO_LOGIN_URL", Type = DbType.String, CaseSensitive = false, Size = 140)]
		public string NomeParametroLoginUrl
		{
			get { return m_nome_parametro_login_url; }
			set { m_nome_parametro_login_url = value; }
		}

		[DataField("MSG_REGISTRAZIONE_COMPLETATA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string MsgRegistrazioneCompletata
		{
			get { return m_msg_registrazione_completata; }
			set { m_msg_registrazione_completata = value; }
		}

		[DataField("MSG_INVIO_PEC", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string MsgInvioPec
		{
			get { return m_msg_invio_pec; }
			set { m_msg_invio_pec = value; }
		}

		[DataField("CODICEOGGETTO_WORKFLOW", Type = DbType.Decimal)]
		public int? CodiceoggettoWorkflow
		{
			get { return m_codiceoggetto_workflow; }
			set { m_codiceoggetto_workflow = value; }
		}

		[DataField("CODICEOGGETTO_MENUXML", Type = DbType.Decimal)]
		public int? CodiceoggettoMenuXml
		{
			get { return m_codiceoggetto_menuxml; }
			set { m_codiceoggetto_menuxml = value; }
		}


		[DataField("NOME_CONFIGURAZIONE_CONTENUTI", Type = DbType.String, CaseSensitive = false, Size = 50)]
		public string NomeConfigurazioneContenuti
		{
			get;
			set;
		}

		[DataField("FKID_SCHEDA_EC", Type = DbType.Decimal)]
		public int? FkidSchedaEc
		{
			get;
			set;
		}
		[DataField("FLG_SCHEDA_EC_RICHIEDEFIRMA", Type = DbType.Decimal)]
		public int? FlgSchedaEcRichiedeFirma
		{
			get;
			set;
		}
		#endregion

		#endregion
	}
}
