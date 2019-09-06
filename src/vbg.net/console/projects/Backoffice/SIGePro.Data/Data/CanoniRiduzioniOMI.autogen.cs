
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
    /// File generato automaticamente dalla tabella CANONI_RIDUZIONIOMI il 13/11/2008 14.38.31
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
    [DataTable("CANONI_RIDUZIONIOMI")]
    [Serializable]
    public partial class CanoniRiduzioniOMI : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_id = null;

        private string m_descrizione = null;

        private double? m_mq_a = null;

        private string m_software = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }
        #endregion

        #region Data fields

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }
        [DataField("MQ_A", Type = DbType.Decimal)]
        public double? MqA
        {
            get { return m_mq_a; }
            set { m_mq_a = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        #endregion

        #endregion
    }
}
