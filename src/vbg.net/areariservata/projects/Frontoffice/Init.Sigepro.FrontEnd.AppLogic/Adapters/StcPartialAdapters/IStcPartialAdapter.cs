using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal interface IStcPartialAdapter
	{
		void Adapt(IDomandaOnlineReadInterface _readInterface, DettaglioPraticaType _dettaglioPratica);
	}
}
