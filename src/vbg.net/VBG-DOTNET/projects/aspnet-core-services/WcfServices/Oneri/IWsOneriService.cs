using Init.SIGePro.Manager.DTO.Oneri;
using System.Collections.Generic;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Oneri
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsOneriService" in both code and config file together.
    [ServiceContract]
    public interface IWsOneriService
    {
        [OperationContract]
        List<OnereDto> GetListaOneriDaIdInterventoECodiciEndo(string token, int codiceIntervento, List<int> listaIdEndo);

        [OperationContract]
        string GetCodiceCausaleOnereTraslazione(string token, int idCausale);


    }
}
