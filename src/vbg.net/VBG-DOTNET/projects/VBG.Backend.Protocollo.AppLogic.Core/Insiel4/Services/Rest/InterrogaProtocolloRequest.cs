using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class InterrogaProtocolloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("registri")]
        public IEnumerable<RicercaRegistro> Registri { get; set; }

        [JsonPropertyName("anno")]
        public IntRange Anno { get; set; }

        [JsonPropertyName("numero")]
        public IntRange Numero { get; set; }

        [JsonPropertyName("verso")]
        [JsonConverter(typeof(VersoConverter))]
        public Verso? Verso { get; set; }

        [JsonPropertyName("data")]
        public DataRange Data { get; set; }

        [JsonPropertyName("oggetto")]
        public ValoreRelazioneUIC Oggetto { get; set; }

        [JsonPropertyName("corrispondente")]
        public CorrispondenteRicerca Corrispondente { get; set; }
    }

    public class RicercaRegistro
    {
        [JsonPropertyName("codiceUfficio")]
        public string CodiceUfficio { get; set; }

        [JsonPropertyName("codiceRegistro")]
        public string CodiceRegistro { get; set; }
    }

    public class CorrispondenteRicerca
    {
        [JsonPropertyName("codice")]
        public string Codice { get; set; }

        [JsonPropertyName("codiceRelazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC? CodiceRelazione { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; }

        [JsonPropertyName("descrizioneRelazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC? DescrizioneRelazione { get; set; }

        [JsonPropertyName("tipo")]
        [JsonConverter(typeof(TipoCorrispondenteConverter))]
        public TipoCorrispondente? Tipo { get; set; }

        [JsonPropertyName("classifica")]
        public Classifica Classifica { get; set; }

        [JsonPropertyName("ricercaNelFascicolo")]
        public IEnumerable<FascicoloId> RicercaNelFascicolo { get; set; }

        [JsonPropertyName("codiceUffOpRicerca")]
        public string CodiceUffOpRicerca { get; set; }

        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("ordinamentoRisultati")]
        [JsonConverter(typeof(OrdinamentoRisultatiConverter))]
        public OrdinamentoRisultati? OrdinamentoRisultati { get; set; }

        [JsonPropertyName("oggettoProtocollo")]
        public ValoreRelazioneUICF OggettoProtocollo { get; set; }

        [JsonPropertyName("estremiDocumento")]
        public EstremiDocumento EstremiDocumento { get; set; }

        [JsonPropertyName("viaTelematica")]
        public bool ViaTelematica { get; set; }

        [JsonPropertyName("conImmagini")]
        public bool ConImmagini { get; set; }

        [JsonPropertyName("conAllegatiInformatici")]
        public bool ConAllegatiInformatici { get; set; }

        [JsonPropertyName("annullate")]
        [JsonConverter(typeof(AnnullateConverter))]
        public Annullate? Annullate { get; set; }

        [JsonPropertyName("scartate")]
        [JsonConverter(typeof(ScartateConverter))]
        public Scartate? Scartate { get; set; }
    }
}
