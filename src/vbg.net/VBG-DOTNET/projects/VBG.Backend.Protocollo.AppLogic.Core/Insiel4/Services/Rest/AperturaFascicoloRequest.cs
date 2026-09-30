using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AperturaFascicoloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("codiceUfficioOperante")]
        public string CodiceUfficioOperante { get; set; }

        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public Classifica CodiceRegistro { get; set; }

        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }

        [JsonPropertyName("anno")]
        public int Anno { get; set; }

        [JsonPropertyName("numero")]
        public int Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public int SubNumero { get; set; }

        [JsonPropertyName("numerazioneManuale")]
        public bool NumerazioneManuale { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("codiceTipoPratica")]
        public string CodiceTipoPratica { get; set; }

        [JsonPropertyName("livSegretezza")]
        public int LivSegretezza { get; set; }

        [JsonPropertyName("uffici")]
        public IEnumerable<UfficioFascicolo> Uffici { get; set; }

        [JsonPropertyName("mnemonici")]
        public IEnumerable<Mnemonico> Mnemonici { get; set; }

        [JsonPropertyName("rifAna")]
        public IEnumerable<RiferimentoAnagrafico> RifAna { get; set; }

        [JsonPropertyName("praticaAc")]
        public DataRange PraticaDataAc { get; set; }

        [JsonPropertyName("iteratti")]
        public bool Iteratti { get; set; }

    }
}
