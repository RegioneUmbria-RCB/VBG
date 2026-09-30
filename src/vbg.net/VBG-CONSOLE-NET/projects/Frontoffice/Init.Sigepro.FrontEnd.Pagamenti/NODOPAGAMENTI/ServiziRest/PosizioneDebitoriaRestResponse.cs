using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Newtonsoft.Json;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest
{
    public class PosizioneDebitoriaRestResponse
    {
        [JsonProperty(propertyName: "id_posizione_debitoria")]
        public int IdPosizioneDebitoria { get; set; }

        [JsonProperty(propertyName: "iuv")]
        public String IUV { get; set; }

        [JsonProperty(propertyName: "codice_avviso")]
        public string CodiceAvviso { get; set; }

        [JsonProperty(propertyName: "qr_code")]
        public String QrCode { get; set; }

        [JsonProperty(propertyName: "descrizione")]
        public String Descrizione { get; set; }

        [JsonProperty(propertyName: "data_registrazione")]
        public DateTime DataRegistrazione { get; set; }

        [JsonProperty(propertyName: "otf")]
        public bool OTF { get; set; }

        [JsonProperty(propertyName: "nominativo_soggetto_debitore")]
        public String NominativoSoggettoDebitore { get; set; }

        [JsonProperty(propertyName: "cf_soggetto_debitore")]
        public String CfSoggettoDebitore { get; set; }

        [JsonProperty(propertyName: "data_scadenza")]
        public DateTime DataScadenza { get; set; }

        [JsonProperty(propertyName: "stato_attuale")]
        public StatoPagamentoResponseType StatoAttuale { get; set; }

        [JsonProperty(propertyName: "importi")]
        public List<ImportoPagamentoResponseType> Importi { get; set; } = new List<ImportoPagamentoResponseType>();

        /*

@XmlElement(name = "pagamenti")
private Set<PagamentiResponseType> pagamenti = new HashSet<>();


*/
    }
}
