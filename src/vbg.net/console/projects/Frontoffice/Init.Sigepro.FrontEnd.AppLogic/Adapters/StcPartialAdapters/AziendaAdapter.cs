using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
	internal class AziendaAdapter : IStcPartialAdapter
	{
		AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

		ITipiSoggettoService _tipiSoggettoService;
        private readonly IFormeGiuridicheRepository _formeGiuridicheRepository;

        public AziendaAdapter(ITipiSoggettoService tipiSoggettoService, IFormeGiuridicheRepository formeGiuridicheRepository)
		{
			this._tipiSoggettoService = tipiSoggettoService;
            this._formeGiuridicheRepository = formeGiuridicheRepository;
        }

		public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
		{
			var azienda = _readInterface.Anagrafiche.GetAzienda();

			if (azienda != null)
			{
				var legaleRappresentante = _readInterface.Anagrafiche.GetLegaleRappresentanteDi(azienda, this._tipiSoggettoService);

				_dettaglioPratica.aziendaRichiedente = _anagraficheHelper.AdattaPersonaGiuridica( azienda, this._formeGiuridicheRepository, legaleRappresentante );
			}
		}
	}
}
