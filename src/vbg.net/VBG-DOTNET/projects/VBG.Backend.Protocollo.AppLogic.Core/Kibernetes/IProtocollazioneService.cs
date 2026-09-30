using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public interface IProtocollazioneService
    {

        ProtocollazioneResponse Protocolla(DatiProtocolloIn datiProtocollo);

    }
}
