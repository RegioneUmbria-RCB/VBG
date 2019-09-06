using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class OTipiOneri
    {
        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return m_id; }
            set { m_id = value; }
        }

        OBaseTipiOnere m_tipiOneriBase = null;
        [ForeignKey("FkBtoId", "Id")]
        public OBaseTipiOnere TipiOneriBase
        {
            get { return m_tipiOneriBase; }
            set { m_tipiOneriBase = value; }
        }


    }
}
