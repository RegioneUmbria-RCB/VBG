
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
	/// File generato automaticamente dalla tabella DYN2_MODELLID il 26/01/2010 12.28.27
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
	[DataTable("DYN2_MODELLID")]
	[Serializable]
	public partial class Dyn2ModelliD : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private int? m_fk_d2mt_id = null;

		private int? m_fk_d2c_id = null;

		private int? m_fk_d2mdt_id = null;

		private int? m_posverticale = null;

		private int? m_posorizzontale = null;

		private int? m_id = null;

		private int? m_flg_multiplo = null;

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

		[isRequired]
		[DataField("FK_D2MT_ID", Type = DbType.Decimal)]
		public int? FkD2mtId
		{
			get { return m_fk_d2mt_id; }
			set { m_fk_d2mt_id = value; }
		}

		[DataField("FK_D2C_ID", Type = DbType.Decimal)]
		public int? FkD2cId
		{
			get { return m_fk_d2c_id; }
			set { m_fk_d2c_id = value; }
		}

		[DataField("FK_D2MDT_ID", Type = DbType.Decimal)]
		public int? FkD2mdtId
		{
			get { return m_fk_d2mdt_id; }
			set { m_fk_d2mdt_id = value; }
		}

		[isRequired]
		[DataField("POSVERTICALE", Type = DbType.Decimal)]
		public int? Posverticale
		{
			get { return m_posverticale; }
			set { m_posverticale = value; }
		}

		[isRequired]
		[DataField("POSORIZZONTALE", Type = DbType.Decimal)]
		public int? Posorizzontale
		{
			get { return m_posorizzontale; }
			set { m_posorizzontale = value; }
		}

		[DataField("FLG_MULTIPLO", Type = DbType.Decimal)]
		public int? FlgMultiplo
		{
			get { return m_flg_multiplo; }
			set { m_flg_multiplo = value; }
		}

        [DataField("FLG_SPEZZA_TABELLA", Type = DbType.Decimal)]
        public int? FlgSpezzaTabella
        {
            get;set;
        }

        #endregion

        #endregion
    }
}
