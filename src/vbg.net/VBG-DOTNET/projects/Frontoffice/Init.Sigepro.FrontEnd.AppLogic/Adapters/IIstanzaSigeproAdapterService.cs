using Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    public interface IIstanzaSigeproAdapterService
    {
        Istanze ToIstanzaBackoffice(IDomandaOnlineReadInterface readInterface);
        Istanze ToIstanzaBackoffice(IDomandaOnlineReadInterface readInterface, IstanzaSigeproAdapterFlags flags);
    }
}