using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class AnagraficaRicerca : IdAnagraficaRicerca
    {
        [JsonPropertyName("codTipoAna")]
        [JsonConverter(typeof(CodiceTipoAnagraficaConverter))]
        public CodiceTipoAnagrafica CodiceTipo { get; set; }

        [JsonPropertyName("codfis")]
        public string CodiceFiscale { get; set; }

        [JsonPropertyName("codfisEstero")]
        public string CodiceFiscaleEstero { get; set; }

        [JsonPropertyName("piva")]
        public string PartitaIva { get; set; }

        [JsonPropertyName("pivaEstero")]
        public string PartitaIvaEstero { get; set; }

        [JsonPropertyName("nome")]
        public string Nome { get; set; }

        [JsonPropertyName("cognome")]
        public string Cognome { get; set; }

        [JsonPropertyName("codUff")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("email")]
        public AnagraficaRicercaEmail Email { get; set; }

        [JsonPropertyName("conCasella")]
        public bool ConCasella { get; set; }

        [JsonPropertyName("attiva")]
        public bool Attiva { get; set; }
    }

    public class AnagraficaRicercaEmail
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC Relazione { get; set; } = OperatoreRelazionaleUIC.uguale;

        [JsonPropertyName("tipo")]
        [JsonConverter(typeof(TipoEmailAnagraficaConverter))]
        public TipoEmailAnagrafica TipoEmail { get; set; } = TipoEmailAnagrafica.pec;
    }
}
