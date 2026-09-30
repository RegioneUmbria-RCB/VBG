
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Data;

namespace Init.SIGePro.Data
{
    public partial class CcICalcoloTContributoRiduz
    {
		CcCausaliRiduzioniR m_causaleRiduzione;
		[ForeignKey("Idcomune, FkCccrrId", "Idcomune, Id")]
		public CcCausaliRiduzioniR CausaleRiduzione
		{
			get { return m_causaleRiduzione; }
			set { m_causaleRiduzione = value; }
		}
	
	}
}
				