using log4net;
using ProtocolloKibernetesService;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public interface IKibernetesProtocollazioneV1
    {
        StatusProtocollo Protocolla(WS_AnagraficaClient ws, ILog logs, IProtocolloSerializer serializer);
    }
}
