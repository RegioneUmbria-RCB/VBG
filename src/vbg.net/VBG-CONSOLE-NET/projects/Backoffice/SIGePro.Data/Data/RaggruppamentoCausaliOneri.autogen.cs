
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
    /// File generato automaticamente dalla tabella RAGGRUPPAMENTOCAUSALIONERI il 03/09/2008 9.29.28
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
    [DataTable("RAGGRUPPAMENTOCAUSALIONERI")]
    [Serializable]
    public partial class RaggruppamentoCausaliOneri : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private string m_software = null;

        private int? m_rco_id = null;

        private string m_rco_descr = null;

        private double? m_importominrateizzazione = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("RCO_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? RcoId
        {
            get { return m_rco_id; }
            set { m_rco_id = value; }
        }


        #endregion

        #region Data fields

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("RCO_DESCR", Type = DbType.String, CaseSensitive = false, Size = 30)]
        public string RcoDescr
        {
            get { return m_rco_descr; }
            set { m_rco_descr = value; }
        }

        [DataField("IMPORTOMINRATEIZZAZIONE", Type = DbType.Decimal)]
        public double? Importominrateizzazione
        {
            get { return m_importominrateizzazione; }
            set { m_importominrateizzazione = value; }
        }

        #endregion

        #endregion
    }
}
