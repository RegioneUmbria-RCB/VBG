
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
    /// File generato automaticamente dalla tabella CANONI_COEFFICIENTI il 11/11/2008 9.18.30
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
    [DataTable("CANONI_COEFFICIENTI")]
    [Serializable]
    public partial class CanoniCoefficienti : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_tsid = null;

        private int? m_anno = null;

        private double? m_coefficiente = null;

        private int? m_periodo = null;

        private string m_software = null;

        private int? m_fk_ccid = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("FK_TSID", Type = DbType.Decimal)]
        public int? FkTsId
        {
            get { return m_fk_tsid; }
            set { m_fk_tsid = value; }
        }

        [KeyField("ANNO", Type = DbType.Decimal)]
        public int? Anno
        {
            get { return m_anno; }
            set { m_anno = value; }
        }

        [KeyField("FK_CCID", Type = DbType.Decimal)]
        public int? FkCcId
        {
            get { return m_fk_ccid; }
            set { m_fk_ccid = value; }
        }


        #endregion

        #region Data fields

        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public double? Coefficiente
        {
            get { return m_coefficiente; }
            set { m_coefficiente = value; }
        }

        [DataField("PERIODO", Type = DbType.Decimal)]
        public int? Periodo
        {
            get { return m_periodo; }
            set { m_periodo = value; }
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
