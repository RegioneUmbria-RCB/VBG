
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
    /// File generato automaticamente dalla tabella CC_CONFIGURAZIONE_SETTORI il 27/06/2008 13.01.37
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
    [DataTable("CC_CONFIGURAZIONE_SETTORI")]
    [Serializable]
    public partial class CCConfigurazioneSettori : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private string m_software = null;

        private string m_fk_se_codicesettore = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String,  Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("SOFTWARE", Type = DbType.String,  Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [KeyField("FK_SE_CODICESETTORE", Type = DbType.String, Size = 6)]
        public string FkSeCodicesettore
        {
            get { return m_fk_se_codicesettore; }
            set { m_fk_se_codicesettore = value; }
        }


        #endregion

        #region Data fields

        #endregion

        #endregion
    }
}
