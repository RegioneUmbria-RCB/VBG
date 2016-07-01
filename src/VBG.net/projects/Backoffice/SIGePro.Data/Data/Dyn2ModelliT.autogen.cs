
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
	/// File generato automaticamente dalla tabella DYN2_MODELLIT il 16/02/2010 16.52.27
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
	[DataTable("DYN2_MODELLIT")]
	[Serializable]
	public partial class Dyn2ModelliT : BaseDataClass
	{
		#region Membri privati

		private string m_idcomune = null;

		private string m_software = null;

		private int? m_id = null;

		private string m_descrizione = null;

		private string m_fk_d2bc_id = null;

		private string m_scriptcode = null;

		private int? m_modellomultiplo = null;

		private int? m_flg_storicizza = null;

		private int? m_flg_readonly_web = null;

		private string m_codice_scheda = null;

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
		[DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
		public string Software
		{
			get { return m_software; }
			set { m_software = value; }
		}

		[isRequired]
		[DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 150)]
		public string Descrizione
		{
			get { return m_descrizione; }
			set { m_descrizione = value; }
		}

		[isRequired]
		[DataField("FK_D2BC_ID", Type = DbType.String, CaseSensitive = false, Size = 2)]
		public string FkD2bcId
		{
			get { return m_fk_d2bc_id; }
			set { m_fk_d2bc_id = value; }
		}

		[DataField("SCRIPTCODE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
		public string Scriptcode
		{
			get { return m_scriptcode; }
			set { m_scriptcode = value; }
		}

		[DataField("MODELLOMULTIPLO", Type = DbType.Decimal)]
		public int? Modellomultiplo
		{
			get { return m_modellomultiplo; }
			set { m_modellomultiplo = value; }
		}

		[DataField("FLG_STORICIZZA", Type = DbType.Decimal)]
		public int? FlgStoricizza
		{
			get { return m_flg_storicizza; }
			set { m_flg_storicizza = value; }
		}

		[DataField("FLG_READONLY_WEB", Type = DbType.Decimal)]
		public int? FlgReadonlyWeb
		{
			get { return m_flg_readonly_web; }
			set { m_flg_readonly_web = value; }
		}
		//[DataField("MODELLO_FRONTOFFICE", Type = DbType.Decimal)]
		//public int? ModelloFrontoffice{get; set;}

		[isRequired]
		[DataField("CODICE_SCHEDA", Type = DbType.String, CaseSensitive = false, Size = 50)]
		public string CodiceScheda
		{
			get { return m_codice_scheda; }
			set { m_codice_scheda = value; }
		}
		#endregion

		#endregion
	}
}
