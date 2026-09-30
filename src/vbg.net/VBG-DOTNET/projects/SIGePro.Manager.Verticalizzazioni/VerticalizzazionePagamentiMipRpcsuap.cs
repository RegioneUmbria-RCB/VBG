
using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazionePagamentiMipRpcsuap : Verticalizzazione
    {
        private static class Constants
        {
            public const string NOME_VERTICALIZZAZIONE = "PAGAMENTI_MIP_RPCSUAP";
            public const string WindowMinutes = "WINDOW_MINUTES";
            public const string UrlServerPagamento = "URLSERVERPAGAMENTO";
            public const string PortaProxy = "PORTAPROXY";
            public const string PortaleID = "PORTALEID";
            public const string EmailPortale = "EMAIL_PORTALE";
            public const string IndirizzoProxy = "INDIRIZZOPROXY";
            public const string IdServizio = "IDSERVIZIO";
            public const string IdentificativoComponente = "IDENTIFICATIVO_COMPONENTE";
            public const string PasswordChiamate = "PASSWORD_CHIAMATE";
            public const string CodiceTipoPagamento = "CODICE_TIPO_PAGAMENTO";
            public const string IntestazioneRicevuta = "INTESTAZIONE_RICEVUTA";

            public const string CodiceUtente = "CODICE_UTENTE";
            public const string CodiceEnte = "CODICE_ENTE";
            public const string TipoUfficio = "TIPO_UFFICIO";
            public const string CodiceUfficio = "CODICE_UFFICIO";
            public const string TipologiaServizio = "TIPOLOGIA_SERVIZIO";
            public const string ChiaveIV = "CHIAVE_IV";
            public const string UrlNotifica = "URL_NOTIFICA";
        }

        public override string NomeVerticalizzazione => Constants.NOME_VERTICALIZZAZIONE;

        public VerticalizzazionePagamentiMipRpcsuap()
        {
        }

        public VerticalizzazionePagamentiMipRpcsuap(string idComuneAlias, string software) : base(idComuneAlias, Constants.NOME_VERTICALIZZAZIONE, software) { }

        //public string NomeVerticalizzazione
        //{
        //    get { return this.GetString(Constants.NomeVerticalizzazione); }
        //    set { this.SetString(Constants.NomeVerticalizzazione, value); }
        //}

        public string WindowMinutes
        {
            get { return this.GetString(Constants.WindowMinutes); }
            set { this.SetString(Constants.WindowMinutes, value); }
        }

        public string UrlServerPagamento
        {
            get { return this.GetString(Constants.UrlServerPagamento); }
            set { this.SetString(Constants.UrlServerPagamento, value); }
        }

        public string PortaProxy
        {
            get { return this.GetString(Constants.PortaProxy); }
            set { this.SetString(Constants.PortaProxy, value); }
        }

        public string PortaleID
        {
            get { return this.GetString(Constants.PortaleID); }
            set { this.SetString(Constants.PortaleID, value); }
        }

        public string EmailPortale
        {
            get { return this.GetString(Constants.EmailPortale); }
            set { this.SetString(Constants.EmailPortale, value); }
        }

        public string IndirizzoProxy
        {
            get { return this.GetString(Constants.IndirizzoProxy); }
            set { this.SetString(Constants.IndirizzoProxy, value); }
        }

        public string IdServizio
        {
            get { return this.GetString(Constants.IdServizio); }
            set { this.SetString(Constants.IdServizio, value); }
        }

        public string IdentificativoComponente
        {
            get { return this.GetString(Constants.IdentificativoComponente); }
            set { this.SetString(Constants.IdentificativoComponente, value); }
        }

        public string PasswordChiamate
        {
            get { return this.GetString(Constants.PasswordChiamate); }
            set { this.SetString(Constants.PasswordChiamate, value); }
        }

        public string CodiceTipoPagamento
        {
            get { return this.GetString(Constants.CodiceTipoPagamento); }
            set { this.SetString(Constants.CodiceTipoPagamento, value); }
        }

        public string IntestazioneRicevuta
        {
            get { return this.GetString(Constants.IntestazioneRicevuta); }
            set { this.SetString(Constants.IntestazioneRicevuta, value); }
        }

        public string CodiceUtente
        {
            get { return this.GetString(Constants.CodiceUtente); }
            set { this.SetString(Constants.CodiceUtente, value); }
        }

        public string CodiceEnte
        {
            get { return this.GetString(Constants.CodiceEnte); }
            set { this.SetString(Constants.CodiceEnte, value); }
        }

        public string TipoUfficio
        {
            get { return this.GetString(Constants.TipoUfficio); }
            set { this.SetString(Constants.TipoUfficio, value); }
        }

        public string CodiceUfficio
        {
            get { return this.GetString(Constants.CodiceUfficio); }
            set { this.SetString(Constants.CodiceUfficio, value); }
        }

        public string TipologiaServizio
        {
            get { return this.GetString(Constants.TipologiaServizio); }
            set { this.SetString(Constants.TipologiaServizio, value); }
        }

        public string ChiaveIV
        {
            get { return this.GetString(Constants.ChiaveIV); }
            set { this.SetString(Constants.ChiaveIV, value); }
        }

        public string UrlNotifica
        {
            get { return this.GetString(Constants.UrlNotifica); }
            set { this.SetString(Constants.UrlNotifica, value); }
        }

    }
}
