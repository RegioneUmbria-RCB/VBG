using Init.SIGePro.Manager.DTO.Mappature;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Mappature
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsMappatureService" in both code and config file together.
    [ServiceContract]
    public interface IWsMappatureService
    {
        [OperationContract]
        MappaturaDto[] GetMappature(string token, string software);
    }
}
