using Init.SIGePro.Manager.DTO.Configurazione;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Configurazione
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsConfigurazioneAreaRiservata" in both code and config file together.
    [ServiceContract]
    public interface IWsConfigurazioneAreaRiservata
    {
        [OperationContract]
        ConfigurazioneAreaRiservataDto LeggiConfigurazioneFrontoffice(string token, string software);

        [OperationContract]
        Init.SIGePro.Data.Configurazione LeggiConfigurazioneComune(string token, string software);
    }
}
