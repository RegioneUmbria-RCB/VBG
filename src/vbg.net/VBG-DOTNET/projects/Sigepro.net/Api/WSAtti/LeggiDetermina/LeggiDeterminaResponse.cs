using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using WSAtti;

namespace Sigepro.net.Api.WSAtti.LeggiDetermina
{
    public class LeggiDeterminaResponse : Response
    {
        [JsonProperty("iddocumento")]
        public int IdDocumento { get; private set; }

        [JsonProperty("codiceclassifica")]
        public string CodiceClassifica { get; private set; }

        [JsonProperty("classifica")]
        public string Classifica { get; private set; }

        [JsonProperty("numeroproposta")]
        public string NumeroProposta { get; internal set; }

        [JsonProperty("annoproposta")]
        public int AnnoProposta { get; private set; }

        [JsonProperty("ufficioproponente")]
        public string UfficioProponente { get; private set; }

        [JsonProperty("strutturaproponente")]
        public string StrutturaProponente { get; private set; }

        [JsonProperty("dirigente")]
        public string Dirigente { get; private set; }

        [JsonProperty("oggetto")]
        public string Oggetto { get; private set; }

        [JsonProperty("numeroatto")]
        public string NumeroAtto { get; private set; }

        [JsonProperty("dataatto")]
        public DateTime? DataAtto { get; internal set; }

        [JsonProperty("annoatto")]
        public int AnnoAtto { get; private set; }

        [JsonProperty("Allegati")]
        public List<AllegatoDetermina> Allegati { get; private set; }

        internal static LeggiDeterminaResponse FromWSAttiLeggiDeterminaResponse(WSAttiLeggiDeterminaResponse response)
        {
            if (response is null)
            {
                throw new ArgumentNullException(nameof(response));
            }

            return new LeggiDeterminaResponse
            {
                Esito = Esito.FromWSEsito(response.Esito),
                IdDocumento = response.Id,
                CodiceClassifica = response.CodiceClassifica,
                Classifica = response.Classifica,
                NumeroProposta = response.NumeroProposta,
                AnnoProposta = response.AnnoProposta,
                UfficioProponente = response.UfficioProponente,
                StrutturaProponente = response.StrutturaProponente,
                Dirigente = response.Dirigente,
                Oggetto = response.Oggetto,
                NumeroAtto = response.NumeroAtto,
                DataAtto = response.DataAtto,
                AnnoAtto = response.AnnoAtto,
                Allegati = response.Allegati?.Select(x => new AllegatoDetermina
                {
                    IdEsterno = x.IdEsterno,
                    IdVBG = x.IdVBG,
                    Image = x.Image,
                    Nome = x.Nome
                }).ToList()
            };
        }
    }
}