using ProtocolloKibernetesService;
using ProtocolloKibernetesV2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class ProtocollazioneResponse
    {
        public int? CodStato { get; internal set; }
        public string DescrStato { get; internal set; }
        public int Anno { get; internal set; }
        public long Numero { get; internal set; }
        public string Warning { get; internal set; }

        internal static ProtocollazioneResponse FromStatusProtocollo(StatusProtocollo response)
        {
            return new ProtocollazioneResponse
            {
                Anno = response.Anno,
                CodStato = response.CodStato,
                DescrStato = response.DescrStato,
                Numero = response.Numero
            };
        }

        internal static ProtocollazioneResponse FromResponseInfo(ResponseInfo wsResponse)
        {
            return new ProtocollazioneResponse
            {
                Anno = wsResponse.ProtocolloResponse.Anno,
                DescrStato = wsResponse.Messaggio,
                Numero = wsResponse.ProtocolloResponse.Numero
            };
        }
    }
}
