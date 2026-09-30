using VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Allegati;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Protocollazione.Protocolla
{
    public class Pratica
    {
        [JsonProperty(PropertyName = "AllegatiList")]
        public IEnumerable<AllegatoWS> AllegatiList { get; set; }

        [JsonProperty(PropertyName = "Assegnata")]
        public bool Assegnata { get; set; }

        [JsonProperty(PropertyName = "Classificazione")]
        public Classificazione Classificazione { get; set;}

        [JsonProperty(PropertyName = "CodiceLivelloOrganigramma")]
        public string CodiceLivelloOrganigramma { get; set; }

        [JsonProperty(PropertyName = "DataConsegnaDocumento")]
        public DateTime? DataConsegnaDocumento { get; set; }

        [JsonProperty(PropertyName = "DataRegistrazione")]
        public DateTime? DataRegistrazione { get; set; }

        [JsonProperty(PropertyName = "IdCodiceAOO")]
        public long IdCodiceAOO { get; set; }

        [JsonProperty(PropertyName = "IdCorrispondentiList")]
        public IEnumerable<IndividuoProtocolloWS> IdCorrispondentiList { get; set; }

        [JsonProperty(PropertyName = "IdOperatore")]
        public long IdOperatore { get; set; }

        [JsonProperty(PropertyName = "idPratica")]
        public long? IdPratica { get; set; }

        [JsonProperty(PropertyName = "InviaMail")]
        public bool InviaMail { get; set; }

        [JsonProperty(PropertyName = "IsFromModificaAllegato")]
        public bool IsFromModificaAllegato { get; set; }

        [JsonProperty(PropertyName = "IsFromModificaCorrispondente")]
        public bool IsFromModificaCorrispondente { get; set; }

        [JsonProperty(PropertyName = "MotivoModifica")]
        public string MotivoModifica { get; set; }

        [JsonProperty(PropertyName = "Note")]
        public string Note { get; set; }

        [JsonProperty(PropertyName = "NumeroProtocollo")]
        public int? NumeroProtocollo { get; set; }

        [JsonProperty(PropertyName = "Oggetto")]
        public string Oggetto { get; set; }

        [JsonProperty(PropertyName = "TipoProtocollo")]
        public string TipoProtocollo { get; set; }
        

    }
}