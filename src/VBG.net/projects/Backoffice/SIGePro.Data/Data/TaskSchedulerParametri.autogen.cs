
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
    /// File generato automaticamente dalla tabella TASKSCHEDULERPARAMETRI il 27/01/2009 9.31.49
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
    [DataTable("TASKSCHEDULERPARAMETRI")]
    [Serializable]
    public partial class TaskSchedulerParametri : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fkidjob = null;

        private string m_parametro = null;

        private string m_valore = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("FKIDJOB", Type = DbType.Decimal)]
        public int? Fkidjob
        {
            get { return m_fkidjob; }
            set { m_fkidjob = value; }
        }

        [KeyField("PARAMETRO", Type = DbType.String, Size = 100)]
        public string Parametro
        {
            get { return m_parametro; }
            set { m_parametro = value; }
        }


        #endregion

        #region Data fields

        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 1000)]
        public string Valore
        {
            get { return m_valore; }
            set { m_valore = value; }
        }

        #endregion

        #endregion
    }
}
