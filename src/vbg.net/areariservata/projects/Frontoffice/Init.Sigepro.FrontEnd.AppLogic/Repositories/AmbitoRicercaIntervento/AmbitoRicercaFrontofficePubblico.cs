using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento
{
	public class AmbitoRicercaFrontofficePubblico: IAmbitoRicercaIntervento
	{
		#region IAmbitoRicercaIntervento Members

		public AmbitoRicerca GetAmbito()
		{
			return AmbitoRicerca.FrontofficePubblico;
		}

		#endregion
	}
}
