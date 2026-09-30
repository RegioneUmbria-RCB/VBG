
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
	/// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOT il 30/06/2008 11.07.01
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
	[DataTable("CC_ICALCOLI_DETTAGLIOT")]
	[Serializable]
	public partial class CCICalcoliDettaglioT : BaseDataClass
	{
		#region Membri privati


		private string m_idcomune = null;

        private int? m_id = null;

        private int? m_codiceistanza = null;

        private int? m_ordine = null;

        private int? m_fk_ccts_id = null;

        private int? m_fk_ccds_id = null;

		private string m_descrizione = null;

        private double? m_su = null;

        private int? m_fk_ccic_id = null;

        private int? m_alloggi = null;

		#endregion

		#region properties



		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = true, Size = 6)]
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

		[isRequired]
		[DataField("CODICEISTANZA", Type = DbType.Decimal)]
		public int? Codiceistanza
		{
			get { return m_codiceistanza; }
			set { m_codiceistanza = value; }
		}

		[isRequired]
		[DataField("ORDINE", Type = DbType.Decimal)]
		public int? Ordine
		{
			get { return m_ordine; }
			set { m_ordine = value; }
		}

		[isRequired]
		[DataField("FK_CCTS_ID", Type = DbType.Decimal)]
		public int? FkCctsId
		{
			get { return m_fk_ccts_id; }
			set { m_fk_ccts_id = value; }
		}

		[DataField("FK_CCDS_ID", Type = DbType.Decimal)]
		public int? FkCcdsId
		{
			get { return m_fk_ccds_id; }
			set { m_fk_ccds_id = value; }
		}

		[DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
		public string Descrizione
		{
			get { return m_descrizione; }
			set { m_descrizione = value; }
		}

		[isRequired]
		[DataField("SU", Type = DbType.Decimal)]
		public double? Su
		{
			get { return m_su; }
			set { m_su = value; }
		}

		[isRequired]
		[DataField("FK_CCIC_ID", Type = DbType.Decimal)]
		public int? FkCcicId
		{
			get { return m_fk_ccic_id; }
			set { m_fk_ccic_id = value; }
		}

		[isRequired]
		[DataField("ALLOGGI", Type = DbType.Decimal)]
		public int? Alloggi
		{
			get { return m_alloggi; }
			set { m_alloggi = value; }
		}

		#endregion

		#endregion
	}
}
