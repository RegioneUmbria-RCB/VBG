using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercaPratiche
{
    public class StradarioIstanzaTrovata
    {
        public int Id { get; set; }
        public string CodiceStradario { get; set; } = "";
        public string Civico { get; set; } = "";
        public string Colore { get; set; } = "";
        public string Note { get; set; } = "";
        public string Stradario { get; set; } = "";
        public string CodiceComune { get; set; } = "";
        public string Esponente { get; set; } = "";
        public string Interno { get; set; } = "";
        public string Scala { get; set; } = "";
        public string EsponenteInterno { get; set; } = "";
        public string Piano { get; set; } = "";
        public string Fabbricato { get; set; } = "";
        public string Km { get; set; } = "";
        public string Circoscrizione { get; set; } = "";
        public string Cap { get; set; } = "";
        public string Latitudine { get; set; } = "";
        public string Longitudine { get; set; } = "";
        public string Uuid { get; set; } = "";
        public string TipoLocalizzazione { get; set; } = "";
        public string CodViario { get; set; } = "";
        public string CodCivico { get; set; } = "";

        public List<MappaleIstanzaTrovata> Mappali { get; set; } = new List<MappaleIstanzaTrovata>();
    }
}
