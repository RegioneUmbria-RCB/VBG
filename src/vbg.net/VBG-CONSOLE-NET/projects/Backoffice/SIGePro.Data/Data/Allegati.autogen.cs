
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
	/// File generato automaticamente dalla tabella ALLEGATI il 30/11/2010 17.27.04
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
	[DataTable("ALLEGATI")]
	[Serializable]
	public partial class Allegati : BaseDataClass
	{
		#region Membri privati

		private int? m_codiceinventario = null;

		private int? m_numeroallegato = null;

		private string m_allegato = null;

		private int? m_amministrazione = null;

		private string m_modello = null;

		private double? m_costo = null;

		private string m_indirizzoweb = null;

		private int? m_codiceoggetto = null;

		private string m_idcomune = null;

		private int? m_pubblica = null;

		private int? m_richiesto = null;

		private int? m_ordine = null;

		private int? m_fo_richiedefirma = null;

		private string m_fo_tipodownload = null;

		private int? m_id = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		[XmlElement(Order = 0)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[KeyField("ID", Type = DbType.Decimal)]
		[useSequence]
		[XmlElement(Order = 1)]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		#endregion

		#region Data fields

		[DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
		[XmlElement(Order = 2)]
		public int? Codiceinventario
		{
			get { return m_codiceinventario; }
			set { m_codiceinventario = value; }
		}

		[DataField("NUMEROALLEGATO", Type = DbType.Decimal)]
		[XmlElement(Order = 3)]
		public int? Numeroallegato
		{
			get { return m_numeroallegato; }
			set { m_numeroallegato = value; }
		}

		[DataField("ALLEGATO", Type = DbType.String, CaseSensitive = false, Size = 512)]
		[XmlElement(Order = 4)]
		public string Allegato
		{
			get { return m_allegato; }
			set { m_allegato = value; }
		}

		[DataField("AMMINISTRAZIONE", Type = DbType.Decimal)]
		[XmlElement(Order = 5)]
		public int? Amministrazione
		{
			get { return m_amministrazione; }
			set { m_amministrazione = value; }
		}

		[DataField("MODELLO", Type = DbType.String, CaseSensitive = false, Size = 255)]
		[XmlElement(Order = 6)]
		public string Modello
		{
			get { return m_modello; }
			set { m_modello = value; }
		}

		[DataField("COSTO", Type = DbType.Decimal)]
		[XmlElement(Order = 7)]
		public double? Costo
		{
			get { return m_costo; }
			set { m_costo = value; }
		}

		[DataField("INDIRIZZOWEB", Type = DbType.String, CaseSensitive = false, Size = 200)]
		[XmlElement(Order = 8)]
		public string Indirizzoweb
		{
			get { return m_indirizzoweb; }
			set { m_indirizzoweb = value; }
		}

		[DataField("CODICEOGGETTO", Type = DbType.Decimal)]
		[XmlElement(Order = 9)]
		public int? Codiceoggetto
		{
			get { return m_codiceoggetto; }
			set { m_codiceoggetto = value; }
		}

		[DataField("PUBBLICA", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public int? Pubblica
		{
			get { return m_pubblica; }
			set { m_pubblica = value; }
		}

		[DataField("RICHIESTO", Type = DbType.Decimal)]
		[XmlElement(Order = 11)]
		public int? Richiesto
		{
			get { return m_richiesto; }
			set { m_richiesto = value; }
		}

		[DataField("ORDINE", Type = DbType.Decimal)]
		[XmlElement(Order = 12)]
		public int? Ordine
		{
			get { return m_ordine; }
			set { m_ordine = value; }
		}

		[DataField("FO_RICHIEDEFIRMA", Type = DbType.Decimal)]
		[XmlElement(Order = 13)]
		public int? FoRichiedefirma
		{
			get { return m_fo_richiedefirma; }
			set { m_fo_richiedefirma = value; }
		}

		[DataField("FO_TIPODOWNLOAD", Type = DbType.String, CaseSensitive = false, Size = 30)]
		[XmlElement(Order = 14)]
		public string FoTipodownload
		{
			get { return m_fo_tipodownload; }
			set { m_fo_tipodownload = value; }
		}

		[DataField("NOTE_FRONTEND", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		[XmlElement(Order = 15)]
		public string NoteFrontend
		{
			get;
			set;
		}

		#endregion

		#endregion
	}
}
