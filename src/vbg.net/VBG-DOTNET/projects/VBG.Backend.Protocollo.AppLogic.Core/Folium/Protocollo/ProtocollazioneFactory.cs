using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Folium.Protocollo
{
    public static class ProtocollazioneFactory
    {
        public static IProtocollazioneFolium Create(RequestInfo info, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            if (!info.Regole.UsaWsMail || info.Flusso == "I")
            {
                return new ProtocollazioneService(info, logs, serializer, bindingFactory);
            }
            else
            {
                return new ProtocollazioneEmailService(info, logs, serializer, bindingFactory);
            }
        }
    }
}
