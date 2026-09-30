using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    internal interface IVisuraIstanzeServiceCreator
    {
        ServiceInstance<IstanzeWsSoapClient> CreateClient();
    }
}