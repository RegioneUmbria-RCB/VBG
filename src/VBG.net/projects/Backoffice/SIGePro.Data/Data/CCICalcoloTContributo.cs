using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Sql.Attributes;
using System.Data;
using Init.SIGePro.Attributes;

namespace Init.SIGePro.Data
{
	public partial class CCICalcoloTContributo
	{
		CCICalcoli m_calcoli = null;

		public CCICalcoli Calcoli
		{
			get { return m_calcoli; }
			set { m_calcoli = value; }
		}

		public double GetQuotaSenzaRiduzioni()
		{
			return ((CostocEdificio.Value / 100.0d) * Coefficiente.Value);
		}

		public double GetQuotaConRiduzioni()
		{
			return GetQuotaSenzaRiduzioni() + Riduzioneperc.Value;
		}
	}
}
