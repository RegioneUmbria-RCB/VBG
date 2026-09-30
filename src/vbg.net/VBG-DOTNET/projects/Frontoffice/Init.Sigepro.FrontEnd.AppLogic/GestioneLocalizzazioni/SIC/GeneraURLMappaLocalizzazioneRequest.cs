using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.SIC
{
    public class GeneraURLMappaLocalizzazioneRequest : GeneraURLMappaRequest
    {
        public string CodiceComune { get; set; } = "";
        public int CodiceStradario { get; set; }
        public string? Km { get; set; }
        public string? Civico { get; set; }
        public string UuidLocalizzazione { get; set; } = "";
        public string? Latitudine { get; set; }
        public string? Longitudine { get; set; }
        public string RiferimentoPratica { get; set; } = "";
    }
}
