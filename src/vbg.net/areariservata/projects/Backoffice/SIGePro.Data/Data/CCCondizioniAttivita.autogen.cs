
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
    /// File generato automaticamente dalla tabella CC_CONDIZIONI_ATTIVITA il 27/06/2008 13.01.37
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
    [DataTable("CC_CONDIZIONI_ATTIVITA")]
    [Serializable]
    public partial class CCCondizioniAttivita : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private string m_fk_at_codiceistat = null;

        private string m_condizionewhere = null;

        private string m_software = null;

        #endregion

        #region properties

        #region Key Fields

		[useSequence]
		[KeyField("ID", Type = DbType.Decimal)]
		public int? Id
		{
			get { return m_id; }
			set { m_id = value; }
		}

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        #endregion

        #region Data fields

        [isRequired]
        [DataField("FK_AT_CODICEISTAT", Type = DbType.String, CaseSensitive = false, Size = 15)]
        public string FkAtCodiceistat
        {
            get { return m_fk_at_codiceistat; }
            set { m_fk_at_codiceistat = value; }
        }

        [DataField("CONDIZIONEWHERE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Condizionewhere
        {
            get { return m_condizionewhere; }
            set { m_condizionewhere = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        #endregion

        #endregion
    }
}
