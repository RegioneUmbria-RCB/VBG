using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class ComuniAssociatiAdapter: IStcPartialAdapter
	{
		ComuniStcHelper _comuniHelper = new ComuniStcHelper();
		
		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			if (!String.IsNullOrEmpty(_readInterface.AltriDati.CodiceComune))
				_dettaglioPratica.codiceComune = _comuniHelper.AdattaComuneDaCodiceBelfiore(_readInterface.AltriDati.CodiceComune);
		}
	}
}
