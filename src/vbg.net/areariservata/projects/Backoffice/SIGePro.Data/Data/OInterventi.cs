using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class OInterventi
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        #region Foreign

        OCCBaseTipoIntervento m_interventoBase = null;
        [ForeignKey("FkOccbtiId", "Id")]
		public OCCBaseTipoIntervento InterventoBase
        {
            get { return m_interventoBase; }
            set { m_interventoBase = value; }
        }

        #endregion
    }
}
