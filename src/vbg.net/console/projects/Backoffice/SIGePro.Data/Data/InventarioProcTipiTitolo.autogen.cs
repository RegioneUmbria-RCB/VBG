
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
	/// File generato automaticamente dalla tabella INVENTARIOPROC_TIPITITOLO il 20/05/2011 10.54.42
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
	[DataTable("INVENTARIOPROC_TIPITITOLO")]
	[Serializable]
	public partial class InventarioProcTipiTitolo : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private int? m_id = null;

		private int? m_codiceinventario = null;

		private string m_tipotitolo = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		[XmlElement(Order=0)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[KeyField("ID", Type = DbType.Decimal)]
		[XmlElement(Order = 1)]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}


		#endregion

		#region Data fields

		[isRequired]
		[DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
		[XmlElement(Order = 2)]
		public int? Codiceinventario
		{
			get { return m_codiceinventario; }
			set { m_codiceinventario = value; }
		}

		[isRequired]
		[DataField("TIPOTITOLO", Type = DbType.String, CaseSensitive = false, Size = 100)]
		[XmlElement(Order = 3)]
		public string Tipotitolo
		{
			get { return m_tipotitolo; }
			set { m_tipotitolo = value; }
		}

		[isRequired]
		[DataField("FLG_MOSTRA_DATA", Type = DbType.Decimal)]
		[XmlElement(Order = 4)]
		public int? FlgMostraData
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_MOSTRA_NUMERO", Type = DbType.Decimal)]
		[XmlElement(Order = 5)]
		public int? FlgMostraNumero
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_MOSTRA_RILASCIATO_DA", Type = DbType.Decimal)]
		[XmlElement(Order = 6)]
		public int? FlgMostraRilasciatoDa
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_RICHIEDE_ALLEGATO", Type = DbType.Decimal)]
		[XmlElement(Order = 7)]
		public int? FlgRichiedeAllegato
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_VERIFICA_FIRMA_ALLEGATO", Type = DbType.Decimal)]
		[XmlElement(Order = 8)]
		public int? FlgVerificaFirmaAllegato
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_NON_PUBBLICARE", Type = DbType.Decimal)]
		[XmlElement(Order = 9)]
		public int? FlgNonPubblicare
		{
			get;
			set;
		}

		[isRequired]
		[DataField("FLG_ALL_OBBLIGATORIO", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public int? FlgAllObbligatorio
		{
			get;
			set;
		}

		
		

		#endregion

		#endregion
	}
}
