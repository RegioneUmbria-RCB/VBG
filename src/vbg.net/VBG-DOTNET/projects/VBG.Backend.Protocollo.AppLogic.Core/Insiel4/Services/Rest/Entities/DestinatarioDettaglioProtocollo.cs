using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class DestinatarioDettaglioProtocollo : Corrispondente
    {
        [JsonPropertyName("dataCarico")]
        public DateTime? DataCarico { get; set; }

        [JsonPropertyName("dataCaricoSpecified")]
        public bool DataCaricoSpecified { get; set; }

        [JsonPropertyName("flIop")]
        public int? FlIop { get; set; }

        [JsonPropertyName("flTipoCasella")]
        public int? FlTipoCasella { get; set; }

        [JsonPropertyName("flTipoInoltro")]
        public int? FlTipoInoltro { get; set; }

        [JsonPropertyName("flStatoPec")]
        public int? FlStatoPec { get; set; }

        [JsonPropertyName("invioTelemTipoMessaggio")]
        [JsonConverter(typeof(DestinatarioInvioTelemTipoMessaggioConverter))]
        public DestinatarioInvioTelemTipoMessaggio InvioTelemTipoMessaggio { get; set; }

        [JsonPropertyName("invioTelemTipoMessaggioSpecified")]
        public bool InvioTelemTipoMessaggioSpecified { get; set; }

        [JsonPropertyName("invioTelemStatoConsegna")]
        [JsonConverter(typeof(DestinatarioInvioTelemStatoConsegnaConverter))]
        public DestinatarioInvioTelemStatoConsegna InvioTelemStatoConsegna { get; set; }

        [JsonPropertyName("invioTelemStatoConsegnaSpecified")]
        public bool InvioTelemStatoConsegnaSpecified { get; set; }

        [JsonPropertyName("invioTelemStatoInvio")]
        [JsonConverter(typeof(DestinatarioInvioTelemStatoInvioConverter))]
        public DestinatarioInvioTelemStatoInvio InvioTelemStatoInvio { get; set; }

        [JsonPropertyName("invioTelemStatoInvioSpecified")]
        public bool InvioTelemStatoInvioSpecified { get; set; }

        [JsonPropertyName("casellaTrasmissioneTelematica")]
        public string CasellaTrasmissioneTelematica { get; set; }

        [JsonPropertyName("invioTelemTipoCasella")]
        [JsonConverter(typeof(DestinatarioInvioTelemTipoCasellaConverter))]
        public DestinatarioInvioTelemTipoCasella InvioTelemTipoCasella { get; set; }

        [JsonPropertyName("invioTelemTipoCasellaSpecified")]
        public bool InvioTelemTipoCasellaSpecified { get; set; }
    }
}
