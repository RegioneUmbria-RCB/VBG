using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.CercaPratiche
{
    public class CercaPraticheWS
    {
        [JsonProperty(PropertyName = "idPratica")]
        public long IdPratica { get; set; }

        [JsonProperty(PropertyName = "codiceAoo")]
        public string CodiceAoo { get; set; }

        [JsonProperty(PropertyName = "oggetto")]
        public string Oggetto { get; set; }

        [JsonProperty(PropertyName = "titolario")]
        public string Titolario { get; set; }

        [JsonProperty(PropertyName = "datiAnnullamento")]
        public DatiAnnullamento DatiAnnullamento { get; set; }

        [JsonProperty(PropertyName = "listaCorrispondenti")]
        public List<Corrispondente> ListaCorrispondenti { get; set; }

        [JsonProperty(PropertyName = "datiRegistrazioneCorrispondente")]
        public DatiRegistrazioneCorrispondente DatiRegistrazioneCorrispondente { get; set; }

        [JsonProperty(PropertyName = "datiRegistrazioneRepertorio")]
        public DatiRegistrazioneRepertorio DatiRegistrazioneRepertorio { get; set; }

        [JsonProperty(PropertyName = "dettaglioTipoDocumento")]
        public DettaglioTipoDocumento DettaglioTipoDocumento { get; set; }
    }
}
