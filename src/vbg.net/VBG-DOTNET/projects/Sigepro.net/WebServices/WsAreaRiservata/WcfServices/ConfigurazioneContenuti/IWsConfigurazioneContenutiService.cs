using Init.SIGePro.Manager.DTO.Configurazione;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.ConfigurazioneContenuti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsConfigurazioneContenutiService" in both code and config file together.
    [ServiceContract]
    public interface IWsConfigurazioneContenutiService
    {
        [OperationContract]
        ConfigurazioneContenutiDto GetConfigurazioneContenutiFrontoffice(string token, string software);
        [OperationContract]
        int GetCodiceOggettoRisorsaFrontoffice(string token, string idRisorsaOggetto);
    }
}
