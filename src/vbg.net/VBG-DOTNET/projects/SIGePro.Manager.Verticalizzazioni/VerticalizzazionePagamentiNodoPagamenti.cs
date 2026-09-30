using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace SIGePro.Manager.Verticalizzazioni
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

        public override string NomeVerticalizzazione => Constants.NomeVerticalizzazione;

        public VerticalizzazionePagamentiNodoPagamenti() : base()
        {
            
        }

        public VerticalizzazionePagamentiNodoPagamenti(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, Constants.NomeVerticalizzazione, software, codiceComune) { }

        public string UrlWs => this.GetString(Constants.UrlWs);

        public string CodiceFiscaleEnteCreditore => this.GetString(Constants.CodiceFiscaleEnteCreditore);

        public string UrlBack
        {
            get
            {
                var url = this.GetString(Constants.UrlBack);

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
                var url = this.GetString(Constants.UrlRitorno);

                if (url.IndexOf("?") == -1)
                {
                    url += "?";
                }

                return url;
            }
        }

        public int? IdModalitaPagamento => this.GetInt(Constants.IdModalitaPagamento);

        public bool ArAttivaPagoDopo => this.GetInt(Constants.ArAttivaPagoDopo).GetValueOrDefault(0) == 1;

        public int ArPagoDopoGGScadenza => this.GetInt(Constants.ArPagoDopoGGScadenza).GetValueOrDefault(30);

        public string SoggettoPendenza
        {
            get
            {
                var sogg = this.GetString(Constants.SoggettoPendenza);

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
