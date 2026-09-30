using System;

namespace Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti
{
    public partial class PraticaAccessoAtti
    {
        public string StringaProtocollo => FormatStringaProtocollo();
        public string StringaNumeroIstanza => $"{this.NumeroIstanza} del {this.DataPresentazione.ToString("dd/MM/yyyy")}";

        private string FormatStringaProtocollo()
        {
            if (string.IsNullOrEmpty(this.NumeroProtocollo))
            {
                return string.Empty;
            }

            if (this.DataProtocollo.HasValue)
            {
                return $"{this.NumeroProtocollo} del {this.DataProtocollo.Value:dd/MM/yyyy}";
            }

            return this.NumeroProtocollo;
        }
    }
}
