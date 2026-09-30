
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class LayoutTestiBase
    {
        [DataField("TESTO", Type = DbType.String, CaseSensitive = false, Size = 200, Compare="like")]
        public string Testo
        {
            get { return m_testo; }
            set { m_testo = value; }
        }

	}
}
				