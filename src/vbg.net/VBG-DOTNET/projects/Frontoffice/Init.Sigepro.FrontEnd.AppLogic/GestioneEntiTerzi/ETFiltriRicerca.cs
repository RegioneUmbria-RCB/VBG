using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public class ETFiltriRicerca
    {
        public DateTime DallaData { get; set; } = DateTime.MinValue;
        public DateTime AllaData { get; set; } = DateTime.MaxValue;
        public bool? Elaborata { get; set; } = (bool?)null;
        public string Software { get; set; } = String.Empty;
        public string NumeroProtocollo { get; set; } = String.Empty;
        public string NumeroIstanza { get; set; } = String.Empty;
    }
}