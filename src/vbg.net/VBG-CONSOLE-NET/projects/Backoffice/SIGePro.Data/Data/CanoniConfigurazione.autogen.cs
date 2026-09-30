
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
    /// File generato automaticamente dalla tabella CANONI_CONFIGURAZIONE il 11/11/2008 9.22.48
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
    [DataTable("CANONI_CONFIGURAZIONE")]
    [Serializable]
    public partial class CanoniConfigurazione : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private double? m_percaddizregionale = null;

        private int? m_fk_coid = null;

        private string m_software = null;

        private int? m_fk_coid_totale = null;

        private int? m_anno = null;

        private double? m_percaddizcomunale = null;

        private int? m_fk_coid_addizcomunale = null;

        private double? m_valorebase_omi = null;

        private double? m_coefficiente_omi = null;

        private double? m_canone_minimo = null;

        private int? m_tiporipartizione_omi = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software
        {
            get { return m_software; }
            set { m_software = value; }
        }

        [DataField("CANONE_MINIMO", Type = DbType.Decimal)]
        public double? CanoneMinimo
        {
            get { return m_canone_minimo; }
            set { m_canone_minimo = value; }
        }

        #endregion

        #endregion
    }
}
