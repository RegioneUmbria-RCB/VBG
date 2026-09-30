using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione
{
    public class Assegnazione
    {
        [JsonProperty(PropertyName = "idAssegnazione")]
        public long IdAssegnazione { get; set; }

        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "idWorkflow")]
        public long? IdWorkflow { get; set; }

        [JsonProperty(PropertyName = "note")]
        public string Note { get; set; }

        [JsonProperty(PropertyName = "motivoRifiuto")]
        public string MotivoRifiuto { get; set; }

        [JsonProperty(PropertyName = "codiceLivelloOrganigrammaAssegnatario")]
        public string CodiceLivelloOrganigrammaAssegnatario { get; set; }

        [JsonProperty(PropertyName = "idAssegnatario")]
        public long? IdAssegnatario { get; set; }

        [JsonProperty(PropertyName = "statoAssegnazione")]
        public string StatoAssegnazione { get; set; }

        [JsonProperty(PropertyName = "areaAssegnazione")]
        public string AreaAssegnazione { get; set; }

        [JsonProperty(PropertyName = "tipoAssegnazione")]
        public string TipoAssegnazione { get; set; }

        [JsonProperty(PropertyName = "dataAccettazione")]
        public DateTime? DataAccettazione { get; set; }

        [JsonProperty(PropertyName = "dataChiusura")]
        public DateTime? DataChiusura { get; set; }

        [JsonProperty(PropertyName = "anno")]
        public long? Anno { get; set; }

        [JsonProperty(PropertyName = "descrizioneEsitoRichiesta")]
        public string DescrizioneEsitoRichiesta { get; set; }

        [JsonProperty(PropertyName = "esitoRichiesta")]
        public string EsitoRichiesta { get; set; }

    }
}
