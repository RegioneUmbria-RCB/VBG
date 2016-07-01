using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class OneriAdapter : IStcPartialAdapter
	{
		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			if (_readInterface.Oneri.Oneri.Count() == 0)
				return;

			_dettaglioPratica.oneri = _readInterface.Oneri.Oneri.Select(x => x.ToOneriType()).ToArray();
		}
	}
}
