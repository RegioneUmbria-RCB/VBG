
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
    /// File generato automaticamente dalla tabella CC_DETERMTIPOCALCOLO il 27/06/2008 13.01.37
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
    [DataTable("CC_DETERMTIPOCALCOLO")]
    [Serializable]
    public partial class CCDetermTipoCalcolo : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private string m_fk_occbti_id = null;

        private string m_fk_occbde_id = null;

        private string m_software = null;

        private string m_fk_ccbtc_id = null;

        private int? m_priorita = null;

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
        [DataField("FK_OCCBTI_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbtiId
        {
            get { return m_fk_occbti_id; }
            set { m_fk_occbti_id = value; }
        }

        [isRequired]
        [DataField("FK_OCCBDE_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbdeId
        {
            get { return m_fk_occbde_id; }
            set { m_fk_occbde_id = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [isRequired]
        [DataField("FK_CCBTC_ID", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string FkCcbtcId
        {
            get { return m_fk_ccbtc_id; }
            set { m_fk_ccbtc_id = value; }
        }

        [isRequired]
        [DataField("PRIORITA", Type = DbType.SByte, Size = 1)]
        public int? Priorita
        {
            get { return m_priorita; }
            set { m_priorita = value; }
        }


        #endregion

        #endregion
    }
}
