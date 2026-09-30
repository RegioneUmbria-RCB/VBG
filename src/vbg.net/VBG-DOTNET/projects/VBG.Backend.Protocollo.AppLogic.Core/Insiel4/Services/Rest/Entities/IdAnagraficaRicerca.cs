using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione.Enum;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities
{
    public class IdAnagraficaRicerca
    {
        [JsonPropertyName("codAna")]
        public AnagraficaRicercaCodAna CodiceAnagrafica { get; set; }

        [JsonPropertyName("descAna")]
        public AnagraficaRicercaDescAna DescrizioneAnagrafica { get; set; }
    }

    public class AnagraficaRicercaCodAna
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUIConverter))]
        public Protocollazione.Enum.OperatoreRelazionaleUI Relazione { get; set; } = Protocollazione.Enum.OperatoreRelazionaleUI.uguale;
    }

    public class AnagraficaRicercaDescAna
    {
        [JsonPropertyName("valore")]
        public string Valore { get; set; }

        [JsonPropertyName("relazione")]
        [JsonConverter(typeof(OperatoreRelazionaleUICConverter))]
        public OperatoreRelazionaleUIC Relazione { get; set; } = OperatoreRelazionaleUIC.uguale;
    }
}
