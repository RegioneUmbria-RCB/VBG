using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class OneriTipiRateizzazione
    {
        [useSequence]
        [KeyField("TIPORATEIZZAZIONE", Type = DbType.Decimal)]
        public int? Tiporateizzazione
        {
            get { return m_tiporateizzazione; }
            set { m_tiporateizzazione = value; }
        }
    }
}
