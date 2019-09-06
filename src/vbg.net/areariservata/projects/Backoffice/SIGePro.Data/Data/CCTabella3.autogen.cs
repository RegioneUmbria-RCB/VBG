
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
    /// File generato automaticamente dalla tabella CC_TABELLA3 il 27/06/2008 13.01.40
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
    [DataTable("CC_TABELLA3")]
    [Serializable]
    public partial class CCTabella3 : BaseDataClass
    {
        #region Membri privati


        private string m_idcomune = null;

        private int? m_id = null;

        private string m_descrizione = null;

        private double? m_perc = null;

        private string m_software = null;

        private int? m_fk_ccds_id = null;

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
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione
        {
            get { return m_descrizione; }
            set { m_descrizione = value; }
        }

        [isRequired]
        [DataField("PERC", Type = DbType.Decimal)]
        public double? Perc
        {
            get { return m_perc; }
            set { m_perc = value; }
        }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("FK_CCDS_ID", Type = DbType.Decimal)]
        public int? FkCcDsId
        {
            get { return m_fk_ccds_id; }
            set { m_fk_ccds_id = value; }
        }

        #endregion

        #endregion
    }
}
