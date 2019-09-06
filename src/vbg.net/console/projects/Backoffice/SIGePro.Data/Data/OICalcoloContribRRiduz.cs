
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class OICalcoloContribRRiduz
    {
		OCausaliRiduzioniR m_riduzione;
		[ForeignKey("Idcomune, FkOcrrId", "Idcomune, Id")]
		public OCausaliRiduzioniR Riduzione
		{
			get { return m_riduzione; }
			set { m_riduzione = value; }
		}

	}
}
				