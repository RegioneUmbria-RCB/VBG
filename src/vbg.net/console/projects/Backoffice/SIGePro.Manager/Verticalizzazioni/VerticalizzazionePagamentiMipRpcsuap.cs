using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public partial class VerticalizzazionePagamentiMipRpcsuap : Verticalizzazione
    {
        private static class Constants
        {
            public const string NomeVerticalizzazione = "PAGAMENTI_MIP_RPCSUAP";
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

        public VerticalizzazionePagamentiMipRpcsuap()
        {
        }

        public VerticalizzazionePagamentiMipRpcsuap(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software) { }

        public string NomeVerticalizzazione
        {
            get { return GetString(Constants.NomeVerticalizzazione); }
            set { SetString(Constants.NomeVerticalizzazione, value); }
        }

        public string WindowMinutes
        {
            get { return GetString(Constants.WindowMinutes); }
            set { SetString(Constants.WindowMinutes, value); }
        }

        public string UrlServerPagamento
        {
            get { return GetString(Constants.UrlServerPagamento); }
            set { SetString(Constants.UrlServerPagamento, value); }
        }

        public string PortaProxy
        {
            get { return GetString(Constants.PortaProxy); }
            set { SetString(Constants.PortaProxy, value); }
        }

        public string PortaleID
        {
            get { return GetString(Constants.PortaleID); }
            set { SetString(Constants.PortaleID, value); }
        }

        public string EmailPortale
        {
            get { return GetString(Constants.EmailPortale); }
            set { SetString(Constants.EmailPortale, value); }
        }

        public string IndirizzoProxy
        {
            get { return GetString(Constants.IndirizzoProxy); }
            set { SetString(Constants.IndirizzoProxy, value); }
        }

        public string IdServizio
        {
            get { return GetString(Constants.IdServizio); }
            set { SetString(Constants.IdServizio, value); }
        }

        public string IdentificativoComponente
        {
            get { return GetString(Constants.IdentificativoComponente); }
            set { SetString(Constants.IdentificativoComponente, value); }
        }

        public string PasswordChiamate
        {
            get { return GetString(Constants.PasswordChiamate); }
            set { SetString(Constants.PasswordChiamate, value); }
        }

        public string CodiceTipoPagamento
        {
            get { return GetString(Constants.CodiceTipoPagamento); }
            set { SetString(Constants.CodiceTipoPagamento, value); }
        }

        public string IntestazioneRicevuta
        {
            get { return GetString(Constants.IntestazioneRicevuta); }
            set { SetString(Constants.IntestazioneRicevuta, value); }
        }

        public string CodiceUtente
        {
            get { return GetString(Constants.CodiceUtente); }
            set { SetString(Constants.CodiceUtente, value); }
        }

        public string CodiceEnte
        {
            get { return GetString(Constants.CodiceEnte); }
            set { SetString(Constants.CodiceEnte, value); }
        }

        public string TipoUfficio
        {
            get { return GetString(Constants.TipoUfficio); }
            set { SetString(Constants.TipoUfficio, value); }
        }

        public string CodiceUfficio
        {
            get { return GetString(Constants.CodiceUfficio); }
            set { SetString(Constants.CodiceUfficio, value); }
        }

        public string TipologiaServizio
        {
            get { return GetString(Constants.TipologiaServizio); }
            set { SetString(Constants.TipologiaServizio, value); }
        }

        public string ChiaveIV
        {
            get { return GetString(Constants.ChiaveIV); }
            set { SetString(Constants.ChiaveIV, value); }
        }

        public string UrlNotifica
        {
            get { return GetString(Constants.UrlNotifica); }
            set { SetString(Constants.UrlNotifica, value); }
        }

    }
}
