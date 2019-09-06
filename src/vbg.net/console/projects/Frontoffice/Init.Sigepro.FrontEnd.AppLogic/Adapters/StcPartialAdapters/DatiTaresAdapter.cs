using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class DatiTaresAdapter : IStcPartialAdapter
	{
		private static class Constants
		{
			public const string DatiUtenzaTaresBari = "Bari.Tares.DatiUtenza";
		}

		ParametriHelper _parametriHelper = new ParametriHelper();

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			if (_readInterface.TaresBari.DatiContribuente == null)
				return;

			_dettaglioPratica.altriDati = _dettaglioPratica.altriDati
															.Union(	new ParametroType[]{ 
																		_parametriHelper.CreaParametroType( Constants.DatiUtenzaTaresBari, _readInterface.TaresBari.DatiContribuente.ToXmlString() )
															}).ToArray();
		}
	}
}
