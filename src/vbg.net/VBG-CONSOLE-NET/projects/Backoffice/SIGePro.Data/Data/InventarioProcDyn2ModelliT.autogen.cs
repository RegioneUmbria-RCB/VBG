
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
	/// File generato automaticamente dalla tabella INVENTARIOPROCDYN2MODELLIT il 01/04/2011 14.36.33
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
	[DataTable("INVENTARIOPROCDYN2MODELLIT")]
	[Serializable]
	public partial class InventarioProcDyn2ModelliT : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private int? m_codiceinventario = null;

		private int? m_fk_d2mt_id = null;

		private int? m_flag_pubblica = null;

		private int? m_flag_tipofirma = null;

		#endregion

		#region properties

		#region Key Fields


		[KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}

		[KeyField("CODICEINVENTARIO", Type = DbType.Decimal)]
		public int? Codiceinventario
		{
			get { return m_codiceinventario; }
			set { m_codiceinventario = value; }
		}

		[KeyField("FK_D2MT_ID", Type = DbType.Decimal)]
		public int? FkD2mtId
		{
			get { return m_fk_d2mt_id; }
			set { m_fk_d2mt_id = value; }
		}


		#endregion

		#region Data fields

		[DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
		public int? FlagPubblica
		{
			get { return m_flag_pubblica; }
			set { m_flag_pubblica = value; }
		}

		[DataField("FLAG_TIPOFIRMA", Type = DbType.Decimal)]
		public int? FlagTipofirma
		{
			get { return m_flag_tipofirma; }
			set { m_flag_tipofirma = value; }
		}

		[DataField("FK_TIPILOCALIZZAZIONE_ID", Type = DbType.Decimal)]
		public int? FkTipilocalizzazioneId
		{
			get;
			set;
		}

		#endregion

		#endregion
	}
}
