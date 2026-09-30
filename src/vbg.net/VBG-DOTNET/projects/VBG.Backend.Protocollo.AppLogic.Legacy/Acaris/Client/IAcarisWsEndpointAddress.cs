using System.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Client
{
    public interface IAcarisWsEndpointAddress
    {
        EndpointAddress CreaEndpointAddress();

        BasicHttpBinding CreaBasicHttpBinding(string bindingName, EndpointAddress endPointAddress);
    }
}
