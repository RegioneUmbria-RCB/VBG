using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class CCValiditaCoefficienti
    {
        #region Membri privati

        private DateTime? m_data_da = null;

        private DateTime? m_data_a = null;

        private double? m_costomq_da = null;

        private double? m_costomq_a = null;

        #endregion

        #region Key fields

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #endregion

        #region Data search fields

        [DataField("DATAINIZIOVALIDITA", Type = DbType.DateTime, DbScope = BaseFieldScope.Where , Compare=">=")]
        public DateTime? Data_da
        {
            get { return m_data_da; }
            set { m_data_da = VerificaDataLocale(value); }
        }

        
        [DataField("DATAINIZIOVALIDITA", Type = DbType.DateTime, DbScope = BaseFieldScope.Where, Compare = "<=")]
        public DateTime? Data_a
        {
            get { return m_data_a; }
            set { m_data_a = VerificaDataLocale(value); }
        }

        [DataField("COSTOMQ", Type = DbType.DateTime, DbScope = BaseFieldScope.Where, Compare = ">=")]
        public double? Costomq_da
        {
            get { return m_costomq_da; }
            set { m_costomq_da = value; }
        }

        [DataField("COSTOMQ", Type = DbType.DateTime, DbScope = BaseFieldScope.Where, Compare = "<=")]
        public double? Costomq_a
        {
            get { return m_costomq_a; }
            set { m_costomq_a = value; }
        }

        #endregion
    }
}
