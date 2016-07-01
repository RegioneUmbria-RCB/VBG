using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class AltriSoggettiAdapter : IStcPartialAdapter
	{
		AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			_dettaglioPratica.altriSoggetti = _readInterface.Anagrafiche
														.GetAltriSoggetti()
														.Select(x => new AltriSoggettiType
														{
															soggetto = _anagraficheHelper.AdattaAnagrafica(x),
															tipoRapporto = _anagraficheHelper.AdattaRuolo(x),
															anagraficaCollegata = _anagraficheHelper.AdattaAnagrafica(x.AnagraficaCollegata)
														}).ToArray();
		}
	}
}
