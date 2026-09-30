
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InterrogaFascicoliRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("oggetto")]
        public InterrogazionePraticheRequestOggetto Oggetto { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public Classifica CodiceRegistro { get; set; }

        [JsonPropertyName("anno")]
        public StringRange Anno { get; set; }

        [JsonPropertyName("numero")]
        public StringRange Numero { get; set; }

        [JsonPropertyName("subNumero")]
        public StringRange SubNumero { get; set; }

        [JsonPropertyName("data")]
        public DataRange Data { get; set; }

        [JsonPropertyName("codiceTipoPratica")]
        public string CodiceTipoPratica { get; set; }

        [JsonPropertyName("dataChiusura")]
        public DataRange DataChiusura { get; set; }

        [JsonPropertyName("praticaDataAc")]
        public DataRange PraticaDataAc { get; set; }

        [JsonPropertyName("stato")]
        public string Stato { get; set; }

        [JsonPropertyName("codiceUffOpRicerca")]
        public string CodiceUffOpRicerca { get; set; }

        [JsonPropertyName("praticaAc")]
        [JsonConverter(typeof(PraticaACConverter))]
        public PraticaAC? PraticaAc { get; set; }

        [JsonPropertyName("uffici")]
        public AnagraficaRicerca[] Uffici { get; set; }

        [JsonPropertyName("riferimentiAna")]
        public AnagraficaRicerca[] RiferimentiAna { get; set; }

        [JsonPropertyName("mnemonici")]
        public MnemonicoRicerca[] Mnemonici { get; set; }
    }

    public class InterrogazionePraticheRequestOggetto
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC? Relazione { get; set; }
    }
}
