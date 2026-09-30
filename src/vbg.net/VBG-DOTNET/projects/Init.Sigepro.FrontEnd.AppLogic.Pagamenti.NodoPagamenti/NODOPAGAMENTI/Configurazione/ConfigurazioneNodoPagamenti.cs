using System;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione
{
    public class ConfigurazioneNodoPagamenti : NodoPagamentiSettings
    {
        private static class SoggettiPendenza
        {
            public const string RICHIEDENTE = "RICHIEDENTE";
            public const string AZIENDA = "AZIENDA";
        }

        public readonly bool NodoPagamentiAttivo;
        public readonly SoggettoPendenzaEnum SoggettoPendenza;
        public readonly bool PagoDopoAttivo;

        public ConfigurazioneNodoPagamenti(string codiceComune, bool nodoPagamentiAttivo, bool pagoDopoAttivo, string urlWs, string codiceFiscaleEnteCreditore, string urlRitorno, int? idModalitaPagamento, string soggettoPendenza, int ggScadenzaPagaDopo) :
            base(FakeEmptyString(codiceComune), FakeEmptyString(urlWs), FakeEmptyString(codiceFiscaleEnteCreditore), FakeEmptyString(urlRitorno), idModalitaPagamento, ggScadenzaPagaDopo)
        {
            this.PagoDopoAttivo = pagoDopoAttivo;
            this.NodoPagamentiAttivo = nodoPagamentiAttivo;
            this.SoggettoPendenza = this.DecodificaSoggettoPendenza(soggettoPendenza);
        }

        private static string FakeEmptyString(string par) => String.IsNullOrEmpty(par) ? "-" : par;

        private SoggettoPendenzaEnum DecodificaSoggettoPendenza(string soggettoPendenza)
        {
            if (soggettoPendenza == SoggettiPendenza.AZIENDA)
            {
                return SoggettoPendenzaEnum.Azienda;
            }

            return SoggettoPendenzaEnum.Richiedente;
        }

        public bool ConfigurazioneValida
        {
            get
            {
                if (!this.NodoPagamentiAttivo)
                    return false;

                //Se non è stato impostato il parametro URL_WS della verticalizzazione NODO_PAGAMENTI
                if (String.IsNullOrEmpty(this.UrlWs))
                    return false;

                //Se non è stato impostato il parametro AR_COD_FISC_ENTE_CREDITORE della verticalizzazione NODO_PAGAMENTI
                if (String.IsNullOrEmpty(this.CodiceFiscaleEnteCreditore))
                    return false;

                //Se non è stato impostato il parametro ID_MODALITA_PAGAMENTO della verticalizzazione NODO_PAGAMENTI
                if (!this.IdModalitaPagamento.HasValue)
                    return false;

                //Se non è stato impostato il parametro AR_URL_RITORNO della verticalizzazione NODO_PAGAMENTI
                if (String.IsNullOrEmpty(this.UrlRitorno))
                    return false;

                return true;
            }
        }
    }
}
