using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class AziendaAdapter : IStcPartialAdapter
	{
		AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

		ITipiSoggettoService _tipiSoggettoService;

		public AziendaAdapter(ITipiSoggettoService tipiSoggettoService)
		{
			this._tipiSoggettoService = tipiSoggettoService;
		}

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var azienda = _readInterface.Anagrafiche.GetAzienda();

			if (azienda != null)
			{
				var legaleRappresentante = _readInterface.Anagrafiche.GetLegaleRappresentanteDi(azienda, this._tipiSoggettoService);

				_dettaglioPratica.aziendaRichiedente = _anagraficheHelper.AdattaPersonaGiuridica( azienda, legaleRappresentante );
			}
		}
	}
}
