
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
    /// File generato automaticamente dalla tabella TASKSCHEDULER il 27/01/2009 9.31.32
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
    [DataTable("TASKSCHEDULER")]
    [Serializable]
    public partial class TaskScheduler : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_idjob = null;

        private string m_task = null;

        private string m_descrizione = null;

        private int? m_intervallo = null;

        private DateTime? m_prossimaesecuzione = null;

        private int? m_attivo = null;

        private int? m_inesecuzione = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("IDJOB", Type = DbType.Decimal)]
        [useSequence]
        public int? Idjob
        {
            get { return m_idjob; }
            set { m_idjob = value; }
        }


        #endregion

        #region Data fields

        [DataField("TASK", Type = DbType.String, CaseSensitive = false, Size = 30)]
        public string Task
        {
            get { return m_task; }
            set { m_task = value; }
        }

        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [DataField("INTERVALLO", Type = DbType.Decimal)]
        public int? Intervallo
        {
            get { return m_intervallo; }
            set { m_intervallo = value; }
        }

        [DataField("PROSSIMAESECUZIONE", Type = DbType.DateTime)]
        public DateTime? Prossimaesecuzione
        {
            get { return m_prossimaesecuzione; }
            set { m_prossimaesecuzione = VerificaDataLocale(value); }
        }

        [DataField("ATTIVO", Type = DbType.Decimal)]
        public int? Attivo
        {
            get { return m_attivo; }
            set { m_attivo = value; }
        }

        [DataField("INESECUZIONE", Type = DbType.Decimal)]
        public int? Inesecuzione
        {
            get { return m_inesecuzione; }
            set { m_inesecuzione = value; }
        }

        #endregion

        #endregion
    }
}
