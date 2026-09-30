using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class TecnicoAdapter : IStcPartialAdapter
    {
        private readonly ILogicaRisoluzioneTecnico _logicaRisoluzioneTecnico;
        private readonly AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

        public TecnicoAdapter(ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico)
        {
            this._logicaRisoluzioneTecnico = logicaRisoluzioneTecnico;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            var tecnico = _readInterface.Anagrafiche.GetTecnico(this._logicaRisoluzioneTecnico);

            _dettaglioPratica.intermediario = this._anagraficheHelper.AdattaAnagrafica(tecnico);
        }
    }
}
