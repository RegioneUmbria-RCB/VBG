using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Anagrafiche;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Anagrafiche
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsAnagraficheService" in both code and config file together.
    [ServiceContract]
    public interface IWsAnagraficheService
    {
        [OperationContract]
        Anagrafe GetAnagrafeByUserId(string token, string userId, TipoPersona tipoPersona);

        [OperationContract]
        AnagraficaCompattaDto GetAnagrafeCompattaByUserId(string token, string userId, TipoPersona tipoPersona);
    }
}
