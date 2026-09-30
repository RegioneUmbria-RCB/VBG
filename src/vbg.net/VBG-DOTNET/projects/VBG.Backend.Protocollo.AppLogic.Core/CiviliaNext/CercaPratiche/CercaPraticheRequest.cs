using Newtonsoft.Json;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class CercaPraticheRequest
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long? IdPratica { get; internal set; }

        [JsonProperty(PropertyName = "numeroProtocolloDal")]
        public int? NumeroProtocolloDal { get; internal set; }

        [JsonProperty(PropertyName = "numeroProtocolloAl")]
        public int? NumeroProtocolloAl { get; internal set; }

        [JsonProperty(PropertyName = "dataProtocollazioneDa")]
        public string DataProtocollazioneDa { get; internal set; }

        [JsonProperty(PropertyName = "dataProtocollazioneA")]
        public string DataProtocollazioneA { get; internal set; }


        [JsonProperty(PropertyName = "pagina")]
        public int Pagina { get; internal set; }

        [JsonProperty(PropertyName = "codiceLivelloOraganigramma")]
        public string CodiceLivelloOrganigramma { get; internal set; }

        [JsonProperty(PropertyName = "idOperatore")]
        public long IdOperatore { get; internal set; }
    }
}
