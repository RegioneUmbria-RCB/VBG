using Init.SIGePro.Manager.DTO.IntegrazioneLDP;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.IntegrazioneLDP
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsIntegrazioneLDPService" in both code and config file together.
    [ServiceContract]
    public interface IWsIntegrazioneLDPService
    {
        [OperationContract]
        ConfigurazioneAlberoprocLDPDto GetConfigurazioneAlberoprocLDP(string token, int idIntervento);
        [OperationContract]
        ConfigurazioneAlberoprocLDPDto GetConfigurazioneAlberoprocLDPDaCodiceIstanza(string token, int codiceIstanza);
    }
}
