
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
	/// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOR il 30/06/2008 14.21.15
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
	[DataTable("CC_ICALCOLI_DETTAGLIOR")]
	[Serializable]
	public partial class CCICalcoliDettaglioR : BaseDataClass
	{
		#region Membri privati


		private string m_idcomune = null;

        private int? m_id = null;

		private int? m_codiceistanza = null;

        private int? m_qta = null;

        private double? m_lung = null;

        private double? m_larg = null;

        private int? m_fk_ccicdt_id = null;

		private double? m_su = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String,  Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
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

		[DataField("QTA", Type = DbType.Decimal)]
		public int? Qta
		{
			get { return m_qta; }
			set { m_qta = value; }
		}

		[DataField("LUNG", Type = DbType.Decimal)]
		public double? Lung
		{
			get { return m_lung; }
			set { m_lung = value; }
		}

		[DataField("LARG", Type = DbType.Decimal)]
		public double? Larg
		{
			get { return m_larg; }
			set { m_larg = value; }
		}

		[isRequired]
		[DataField("FK_CCICDT_ID", Type = DbType.Decimal)]
		public int? FkCcicdtId
		{
			get { return m_fk_ccicdt_id; }
			set { m_fk_ccicdt_id = value; }
		}

		[isRequired]
		[DataField("SU", Type = DbType.Decimal)]
		public double? Su
		{
			get { return m_su; }
			set { m_su = value; }
		}

		#endregion

		#endregion
	}
}
