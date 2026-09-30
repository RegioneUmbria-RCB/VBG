using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class TipiAree
    {
        [KeyField("CODICETIPOAREA", Type = DbType.Decimal)]
        [useSequence]
        public int? Codicetipoarea
        {
            get { return m_codicetipoarea; }
            set { m_codicetipoarea = value; }
        }

		public override string ToString()
		{
			return Tipoarea;
		}
    }
}
