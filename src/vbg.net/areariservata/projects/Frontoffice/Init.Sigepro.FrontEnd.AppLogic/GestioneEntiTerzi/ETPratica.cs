using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public class ETPratica
    {
        public int CodiceIstanza { get; internal set; }
        public string NumeroIstanza { get; internal set; }
        public DateTime DataPresentazione { get; internal set; }
        public DateTime? DataProtocollo { get; internal set; }
        public string Localizzazione { get; internal set; }
        public string NumeroProtocollo { get; internal set; }
        public string Oggetto { get; internal set; }
        public string Richiedente { get; internal set; }
        public string StatoLavorazione { get; internal set; }
        public string TipoIntervento { get; internal set; }
        public string UUID { get; internal set; }
        public string Modulo { get; internal set; }

        public string StringaProtocollo => String.IsNullOrEmpty(this.NumeroProtocollo) ? String.Empty : $"{this.NumeroProtocollo} del {this.DataProtocollo.Value.ToString("dd/MM/yyyy")}";
        public string StringaNumeroIstanza  => $"{this.NumeroIstanza} del {this.DataPresentazione.ToString("dd/MM/yyyy")}";
    }
}