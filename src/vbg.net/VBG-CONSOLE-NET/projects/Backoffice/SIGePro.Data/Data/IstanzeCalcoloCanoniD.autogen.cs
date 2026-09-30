
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
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_D il 11/11/2008 9.19.52
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
    [DataTable("ISTANZECALCOLOCANONI_D")]
    [Serializable]
    public partial class IstanzeCalcoloCanoniD : BaseDataClass
    {
        #region Membri privati

        private string m_idcomune = null;

        private int? m_fk_idtestata = null;

        private int? m_fk_tsid = null;

        private double? m_misura = null;

        private double? m_coefficiente = null;

        private double? m_periodo = null;

        private double? m_totale = null;

        private int? m_fk_ccid = null;

        private int? m_id = null;

        private double? m_percriduzione = null;

        private DateTime? m_datada = null;

        private DateTime? m_dataa = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }


        #endregion

        #region Data fields

        [isRequired]
        [DataField("FK_IDTESTATA", Type = DbType.Decimal)]
        public int? FkIdtestata
        {
            get { return m_fk_idtestata; }
            set { m_fk_idtestata = value; }
        }

        [isRequired]
        [DataField("FK_TSID", Type = DbType.Decimal)]
        public int? FkTsid
        {
            get { return m_fk_tsid; }
            set { m_fk_tsid = value; }
        }

        [isRequired]
        [DataField("MISURA", Type = DbType.Decimal)]
        public double? Misura
        {
            get { return m_misura; }
            set { m_misura = value; }
        }


        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public double? Coefficiente
        {
            get { return m_coefficiente; }
            set { m_coefficiente = value; }
        }

        [DataField("PERIODO", Type = DbType.Decimal)]
        public double? Periodo
        {
            get { return m_periodo; }
            set { m_periodo = value; }
        }

        [isRequired]
        [DataField("TOTALE", Type = DbType.Decimal)]
        public double? Totale
        {
            get { return m_totale; }
            set { m_totale = value; }
        }

        [DataField("FK_CCID", Type = DbType.Decimal)]
        public int? FkCcid
        {
            get { return m_fk_ccid; }
            set { m_fk_ccid = value; }
        }


        [DataField("PERCRIDUZIONE", Type = DbType.Decimal)]
        public double? PercRiduzione
        {
            get { return m_percriduzione; }
            set { m_percriduzione = value; }
        }

        [DataField("DATA_DA", Type = DbType.DateTime)]
        public DateTime? DataDa
        {
            get { return m_datada; }
            set { m_datada = value; }
        }

        [DataField("DATA_A", Type = DbType.DateTime)]
        public DateTime? DataA
        {
            get { return m_dataa; }
            set { m_dataa = value; }
        }

        #endregion

        #endregion
    }
}
