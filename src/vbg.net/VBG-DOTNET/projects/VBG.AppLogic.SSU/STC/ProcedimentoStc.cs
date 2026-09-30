using System.Text.Json.Serialization;

namespace VBG.AppLogic.SSU.STC
{
    public class ProcedimentoStc
    {
        [JsonPropertyName("codice")]
        public int Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; } = "";

        [JsonPropertyName("fattispecie")]
        public List<FattispecieStc>? Fattispecie { get; set; }

        [JsonPropertyName("allegati")]
        public List<AllegatoProcedimentoStc>? Allegati { get; set; }

        [JsonPropertyName("schede")]
        public List<SchedaProcedimentoStc>? Schede { get; set; }

        [JsonPropertyName("attivatoDa")]
        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public AttivazioneProcedimentoStc? AttivatoDa { get; set; }
    }

    public class FattispecieStc
    {
        [JsonPropertyName("codice")]
        public int Codice { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; } = "";
    }

    public class AllegatoProcedimentoStc
    {
        [JsonPropertyName("codice")]
        public int Codice { get; set; }

        [JsonPropertyName("codiceOggetto")]
        public int CodiceOggetto { get; set; }

        [JsonPropertyName("descrizione")]
        public string Descrizione { get; set; } = "";

        [JsonPropertyName("nomeFile")]
        public string NomeFile { get; set; } = "";
    }

    public class SchedaProcedimentoStc
    {
        [JsonPropertyName("codice")]
        public int Codice { get; set; }
        [JsonPropertyName("riepiloghi")]
        public List<RiepilogoSchedaProcedimentoStc> Riepiloghi { get; set; } = [];
    }

    public class RiepilogoSchedaProcedimentoStc
    {
        [JsonPropertyName("codiceOggetto")]
        public int CodiceOggetto { get; set; }
        [JsonPropertyName("nomeFile")]
        public string NomeFile { get; set; } = "";
    }

    public class AttivazioneProcedimentoStc
    {
        [JsonPropertyName("fattispecie")]
        public int Fattispecie { get; set; }

        [JsonPropertyName("procedimento")]
        public int Procedimento { get; set; }
    }
}
