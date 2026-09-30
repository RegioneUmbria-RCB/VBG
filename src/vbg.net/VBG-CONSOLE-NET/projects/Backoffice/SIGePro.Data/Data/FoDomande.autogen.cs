
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	///
	/// File generato automaticamente dalla tabella FO_DOMANDE il 23/11/2009 11.03.32
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
	[DataTable("FO_DOMANDE")]
	[Serializable]
	public partial class FoDomande : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private int? m_id = null;

		private string m_software = null;

		private int? m_codiceanagrafe = null;

		private int? m_flg_presentata = null;

		private int? m_flg_trasferita = null;

		private int? m_flg_eliminata = null;

		private int? m_codiceoggetto = null;

		private int? m_codiceistanza = null;

		private DateTime? m_datainvio = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		[XmlElement(Order=3)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}


		#endregion

		#region Data fields

		[DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
		[XmlElement(Order = 4)]
		public string Software
		{
			get { return m_software; }
			set { m_software = value; }
		}

		[isRequired]
		[DataField("CODICEANAGRAFE", Type = DbType.Decimal)]
		[XmlElement(Order = 5)]
		public int? Codiceanagrafe
		{
			get { return m_codiceanagrafe; }
			set { m_codiceanagrafe = value; }
		}

		[isRequired]
		[DataField("FLG_PRESENTATA", Type = DbType.Decimal)]
		[XmlElement(Order = 6)]
		public int? FlgPresentata
		{
			get { return m_flg_presentata; }
			set { m_flg_presentata = value; }
		}

		[isRequired]
		[DataField("FLG_TRASFERITA", Type = DbType.Decimal)]
		[XmlElement(Order = 7)]
		public int? FlgTrasferita
		{
			get { return m_flg_trasferita; }
			set { m_flg_trasferita = value; }
		}

		[isRequired]
		[DataField("FLG_ELIMINATA", Type = DbType.Decimal)]
		[XmlElement(Order = 8)]
		public int? FlgEliminata
		{
			get { return m_flg_eliminata; }
			set { m_flg_eliminata = value; }
		}

		[DataField("CODICEOGGETTO", Type = DbType.Decimal)]
		[XmlElement(Order = 9)]
		public int? Codiceoggetto
		{
			get { return m_codiceoggetto; }
			set { m_codiceoggetto = value; }
		}

		[DataField("CODICEISTANZA", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public int? Codiceistanza
		{
			get { return m_codiceistanza; }
			set { m_codiceistanza = value; }
		}

		[DataField("DATAINVIO", Type = DbType.DateTime)]
		[XmlElement(Order = 11)]
		public DateTime? Datainvio
		{
			get { return m_datainvio; }
			set { m_datainvio = value; }
		}

		[DataField("DATA_ULTIMA_MODIFICA", Type = DbType.DateTime)]
		[XmlElement(Order = 12)]
		public DateTime? DataUltimaModifica{get;set;}

		[DataField("IDENTIFICATIVODOMANDA", Type = DbType.String, CaseSensitive = false, Size = 50)]
		[XmlElement(Order = 13)]
		public string Identificativodomanda
		{
			get;
			set;
		}
		#endregion

		#endregion
	}
}
