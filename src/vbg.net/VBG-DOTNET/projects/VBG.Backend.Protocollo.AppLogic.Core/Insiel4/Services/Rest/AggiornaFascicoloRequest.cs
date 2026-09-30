using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using System;
using System.Text.Json.Serialization;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest
{
    public class AggiornaFascicoloRequest
    {
        [JsonPropertyName("utente")]
        public Utente Utente { get; set; }

        [JsonPropertyName("fascicolo")]
        public RegistrazioneFascicolo Fascicolo { get; set; }

        [JsonPropertyName("data")]
        public DateTime? Data { get; set; }

        [JsonPropertyName("note")]
        public NoteObj Note { get; set; }

        [JsonPropertyName("oggetto")]
        public string Oggetto { get; set; }

        [JsonPropertyName("codiceTipoPratica")]
        public string CodiceTipoPratica { get; set; }

        [JsonPropertyName("dataChiusura")]
        public DataChiusuraObj DataChiusura { get; set; }

        [JsonPropertyName("uffici")]
        public UfficiFascicoloAgg Uffici { get; set; }

        [JsonPropertyName("praticaAc")]
        public StringRange PraticaAc { get; set; }

        [JsonPropertyName("stato")]
        public int Stato { get; set; } //"0 attivo, 1 disattivato/annullato"
    }

    //impostare Note per valorizzare/sovrascrivere il campo, oppure impostare noteVuote a true nel caso si voglia cancellare le note precedenti
    public class NoteObj
    {
        [JsonPropertyName("note")]
        public string Note { get; set; }

        [JsonPropertyName("noteVuote")]
        public bool NoteVuote { get; set; }
    }

    //impostare DataChiusura per valorizzare/sovrascrivere il campo, oppure impostare DataVuota a true nel caso si voglia cancellare il valore precedente
    public class DataChiusuraObj
    {
        [JsonPropertyName("dataChiusura")]
        public DateTime DataChiusura { get; set; }

        [JsonPropertyName("dataVuota")]
        public bool DataVuota { get; set; }
    }
}
