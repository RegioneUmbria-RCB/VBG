using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    public partial class CCConfigurazioneSettori
    {
		Settori m_settore;
		[ForeignKey("Idcomune, FkSeCodicesettore", "IDCOMUNE, CODICESETTORE")]
		public Settori Settore
		{
			get { return m_settore; }
			set { m_settore = value; }
		}

    }
}
