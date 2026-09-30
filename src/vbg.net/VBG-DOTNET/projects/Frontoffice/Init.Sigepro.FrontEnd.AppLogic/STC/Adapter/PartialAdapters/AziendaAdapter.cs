using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class AziendaAdapter : IStcPartialAdapter
    {
        private readonly AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

        private readonly ITipiSoggettoService _tipiSoggettoService;

        public AziendaAdapter(ITipiSoggettoService tipiSoggettoService)
        {
            this._tipiSoggettoService = tipiSoggettoService;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            var azienda = _readInterface.Anagrafiche.GetAzienda();

            if (azienda is not null)
            {
                var legaleRappresentante = _readInterface.Anagrafiche.GetLegaleRappresentanteDi(azienda, this._tipiSoggettoService);

                _dettaglioPratica.aziendaRichiedente = this._anagraficheHelper.AdattaPersonaGiuridica(azienda, legaleRappresentante);
            }
        }
    }
}
