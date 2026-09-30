using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class NodoPagamentiSettings
    {
        public readonly string UrlWs;
        public readonly string CodiceFiscaleEnteCreditore;
        public readonly string UrlRitorno;
        public readonly int? IdModalitaPagamento;
        public readonly int GgScadenzaPagoDopo;
        public readonly UrlServizoRestDatiPosizioneDebitoria UrlServizoRestDatiPosizioneDebitoria;

        public NodoPagamentiSettings(string urlWs, string codiceFiscaleEnteCreditore, string urlRitorno, int? idModalitaPagamento, int ggScadenzaPagoDopo)
        {
            if (string.IsNullOrEmpty(urlWs))
            {
                throw new ArgumentException($"'{nameof(urlWs)}' cannot be null or empty", nameof(urlWs));
            }

            if (string.IsNullOrEmpty(codiceFiscaleEnteCreditore))
            {
                throw new ArgumentException($"'{nameof(codiceFiscaleEnteCreditore)}' cannot be null or empty", nameof(codiceFiscaleEnteCreditore));
            }

            if (string.IsNullOrEmpty(urlRitorno))
            {
                throw new ArgumentException($"'{nameof(urlRitorno)}' cannot be null or empty", nameof(urlRitorno));
            }

            this.UrlWs = FakeEmptyStringToString(urlWs);
            this.CodiceFiscaleEnteCreditore = FakeEmptyStringToString(codiceFiscaleEnteCreditore);
            //this.UrlBack = urlBack;
            this.UrlRitorno = FakeEmptyStringToString(urlRitorno);
            this.IdModalitaPagamento = idModalitaPagamento;
            this.GgScadenzaPagoDopo = ggScadenzaPagoDopo;
            this.UrlServizoRestDatiPosizioneDebitoria = new UrlServizoRestDatiPosizioneDebitoria(this.UrlWs);
        }

        private static string FakeEmptyStringToString(string par) => par == "-" ? String.Empty : par;
    }
}
