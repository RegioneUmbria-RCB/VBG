using ProtocolloKibernetesService;
using ProtocolloKibernetesV2Service;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class InviaAllegatoResponse
    {
        public string DescrStato { get; internal set; }
        public bool Ok { get; internal set; }
        public string[] Errori { get; private set; }

        internal static InviaAllegatoResponse FromResponseInfo(ResponseInfo wsResponse)
        {
            return new InviaAllegatoResponse
            {
                DescrStato = wsResponse.Messaggio,
                Errori = wsResponse.Errori,
                Ok = (wsResponse.Errori == null || wsResponse.Errori.Length == 0)
            };
        }

        internal static InviaAllegatoResponse FromStatusCheckProtocollo(StatusCheckProtocollo response)
        {
            return new InviaAllegatoResponse
            {
                DescrStato = response.Descizione,
                Errori = new string[] { response.Descizione },
                Ok = !response.CodStato.Equals(1)
            };
        }

        internal static InviaAllegatoResponse FromException(Exception ex)
        {
            return new InviaAllegatoResponse
            {
                DescrStato = ex.Message,
                Errori = new string[] { ex.Message },
                Ok = false
            };
        }
    }
}
