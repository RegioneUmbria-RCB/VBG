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
    [DataTable("MERCATI_D_CONTI")]
    [Serializable]
    public partial class Mercati_DConti : BaseDataClass
    {
        #region Membri privati

        private int? m_id = null;

        private int? m_fk_mdid = null;

        private int? m_fk_coid = null;

        private int? m_anno = null;

        private double? m_valore = null;

        private int? m_flag_canone = null;

        private string m_idcomune = null;

        private int? m_flag_valore = null;

        private string m_contesto = null;

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



        #endregion
    }
}
