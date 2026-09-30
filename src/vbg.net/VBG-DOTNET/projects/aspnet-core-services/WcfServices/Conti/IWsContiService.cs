using Init.SIGePro.Manager.DTO.Oneri;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Conti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsContiService" in both code and config file together.
    [ServiceContract]
    public interface IWsContiService
    {
        [OperationContract]
        ContoDto GetContoDaIdCausaleOnere(string token, int idCausale);
    }
}
