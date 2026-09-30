using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazionePagamentiNodoPagamenti : Verticalizzazione
    {
        private class Constants
        {
            public const string NomeVerticalizzazione = "NODO_PAGAMENTI";
            public const string UrlWs = "URL_WS";
            public const string CodiceFiscaleEnteCreditore = "AR_COD_FISC_ENTE_CREDITORE";
            public const string UrlBack = "AR_URL_BACK";
            public const string UrlRitorno = "AR_URL_RITORNO";
            public const string IdModalitaPagamento = "ID_MODALITA_PAGAMENTO";
            public const string SoggettoPendenza = "SOGGETTO_PENDENZA";
            public const string SoggettoPendenzaValoreAzienda = "AZIENDA";
            public const string SoggettoPendenzaValoreRichiedente = "RICHIEDENTE";
            public const string ArAttivaPagoDopo = "AR_ATTIVA_PAGO_DOPO";
            public const string ArPagoDopoGGScadenza = "AR_PAGO_DOPO_GG_SCADENZA";
        }

        public VerticalizzazionePagamentiNodoPagamenti(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NomeVerticalizzazione, software, codiceComune) { }

        public string UrlWs => GetString(Constants.UrlWs);

        public string CodiceFiscaleEnteCreditore => GetString(Constants.CodiceFiscaleEnteCreditore);

        public string UrlBack
        {
            get
            {
                var url = GetString(Constants.UrlBack);

                if (url.IndexOf("?") == -1)
                {
                    url += "?";
                }

                return url;
            }
        }

        public string UrlRitorno
        {
            get
            {
                var url = GetString(Constants.UrlRitorno);

                if (url.IndexOf("?") == -1)
                {
                    url += "?";
                }

                return url;
            }
        }

        public int? IdModalitaPagamento => GetInt(Constants.IdModalitaPagamento);

        public bool ArAttivaPagoDopo => GetInt(Constants.ArAttivaPagoDopo).GetValueOrDefault(0) == 1;

        public int ArPagoDopoGGScadenza => GetInt(Constants.ArPagoDopoGGScadenza).GetValueOrDefault(30);

        public string SoggettoPendenza
        {
            get
            {
                var sogg = GetString(Constants.SoggettoPendenza);

                if (String.IsNullOrEmpty(sogg))
                {
                    return Constants.SoggettoPendenzaValoreRichiedente;
                }

                // Scarto eventuali valori non validi. Il campo può solo contenere
                // "AZIENDA" o "RICHIEDENTE"
                if (sogg.ToUpper() != Constants.SoggettoPendenzaValoreAzienda)
                {
                    return Constants.SoggettoPendenzaValoreRichiedente;
                }

                return Constants.SoggettoPendenzaValoreAzienda;
            }
        }
    }
}
