using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    public interface IStcPartialAdapter
    {
        void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica);
    }
}
