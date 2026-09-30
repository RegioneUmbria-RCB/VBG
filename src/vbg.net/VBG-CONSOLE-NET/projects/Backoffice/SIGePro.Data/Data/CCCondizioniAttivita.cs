using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CCCondizioniAttivita
    {
		Attivita m_attivita;
		[ForeignKey("Idcomune, FkAtCodiceistat", "IDCOMUNE, CodiceIstat")]
		public Attivita Attivita
		{
			get { return m_attivita; }
			set { m_attivita = value; }
		}

    }
}
