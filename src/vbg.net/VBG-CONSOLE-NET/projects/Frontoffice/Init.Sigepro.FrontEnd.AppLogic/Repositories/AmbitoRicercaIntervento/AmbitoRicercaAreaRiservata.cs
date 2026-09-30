using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento
{
	public class AmbitoRicercaAreaRiservata : IAmbitoRicercaIntervento
	{
		bool _utenteTester;

		public AmbitoRicercaAreaRiservata(bool utenteTester)
		{
			this._utenteTester = utenteTester;
		}

		#region IAmbitoRicercaIntervento Members

		public AmbitoRicerca GetAmbito()
		{
			if (this._utenteTester)
				return AmbitoRicerca.UtenteTesterAreaRiservata;

			return AmbitoRicerca.AreaRiservata;
		}

		#endregion
	}
}
