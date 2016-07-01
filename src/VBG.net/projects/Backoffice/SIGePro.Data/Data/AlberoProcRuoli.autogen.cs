
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
    /// File generato automaticamente dalla tabella ALBEROPROC_DYN2MODELLIT il 05/08/2008 16.49.57
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
    [DataTable("ALBEROPROC_RUOLI")]
    [Serializable]
    public partial class AlberoProcRuoli : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_sc_id = null;

        private int? m_fk_idruolo = null;

        #endregion

        #region properties

        #region Key Fields

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("FK_SC_ID", Type = DbType.Decimal)]
        public int? FkScId
        {
            get { return m_fk_sc_id; }
            set { m_fk_sc_id = value; }
        }

        [KeyField("FK_IDRUOLO", Type = DbType.Decimal)]
        public int? FkIdRuolo
        {
            get { return m_fk_idruolo; }
            set { m_fk_idruolo = value; }
        }


        #endregion

        #region Data fields

        #endregion

        #endregion
    }
}
