using System.ServiceModel;

namespace VBG.Backend.SIT.AppLogic.SitLdp.ServiceReferences
{
    public interface ILDPSoapClient
    {
        IClientChannel InnerChannel { get; }
        void Abort();
    }
}
