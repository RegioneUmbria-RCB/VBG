
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
    /// File generato automaticamente dalla tabella CC_COEFFCONTRIB_ATTIVITA il 01/07/2008 16.07.06
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
    [DataTable("CC_COEFFCONTRIB_ATTIVITA")]
    [Serializable]
    public partial class CCCoeffContribAttivita : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private int? m_fk_ccvc_id = null;

        private int? m_fk_ccde_id = null;

        private int? m_fk_ccca_id = null;

        private double? m_coefficiente = null;

        private string m_software = null;

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
        [DataField("FK_CCVC_ID", Type = DbType.Decimal)]
        public int? FkCcvcId
        {
            get { return m_fk_ccvc_id; }
            set { m_fk_ccvc_id = value; }
        }

        [isRequired]
        [DataField("FK_CCDE_ID", Type = DbType.Decimal)]
        public int? FkCcdeId
        {
            get { return m_fk_ccde_id; }
            set { m_fk_ccde_id = value; }
        }

        [DataField("FK_CCCA_ID", Type = DbType.Decimal)]
        public int? FkCccaId
        {
            get { return m_fk_ccca_id; }
            set { m_fk_ccca_id = value; }
        }

        [isRequired]
        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public double? Coefficiente
        {
            get { return m_coefficiente; }
            set { m_coefficiente = value; }
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
