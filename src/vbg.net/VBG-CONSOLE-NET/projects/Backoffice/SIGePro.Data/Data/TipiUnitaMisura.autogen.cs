
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
	/// File generato automaticamente dalla tabella TIPIUNITAMISURA il 15/10/2008 15.50.09
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
	[DataTable("TIPIUNITAMISURA")]
	[Serializable]
	public partial class TipiUnitaMisura : BaseDataClass
	{
		#region Membri privati

        private int? m_um_id = null;

		private string m_um_descrbreve = null;

		private string m_um_descrestesa = null;

		private string m_idcomune = null;

		#endregion
		
		#region properties
		
		#region Key Fields
		
		
		[KeyField("UM_ID" , Type=DbType.Decimal)]
        [useSequence]
		public int? UmId
		{
			get{ return m_um_id; }
			set{ m_um_id = value; }
		}
		
		[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
		public string Idcomune
		{
			get{ return m_idcomune; }
			set{ m_idcomune = value; }
		}
		
		
		#endregion
		
		#region Data fields
		
		[isRequired]
[DataField("UM_DESCRBREVE" , Type=DbType.String, CaseSensitive=false, Size=30)]
		public string UmDescrbreve
		{
			get{ return m_um_descrbreve; }
			set{ m_um_descrbreve = value; }
		}
		
		[DataField("UM_DESCRESTESA" , Type=DbType.String, CaseSensitive=false, Size=100)]
		public string UmDescrestesa
		{
			get{ return m_um_descrestesa; }
			set{ m_um_descrestesa = value; }
		}
		
		#endregion

		#endregion
	}
}
				