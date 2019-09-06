using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazionePagamentiEntraNext : Verticalizzazione
    {
        private class Constants
        {
            public const string NomeVerticalizzazione = "PAGAMENTI_ENTRANEXT";
            public const string UrlWs = "URL_WS";
            public const string IdentificativoConnettore = "IDENTIFICATIVO_CONNETTORE";
            public const string CodiceFiscaleEnte = "CODICE_FISCALE_ENTE";
            public const string Versione = "VERSIONE";
            public const string Identificativo = "IDENTIFICATIVO";
            public const string Username = "USERNAME";
            public const string PasswordMd5 = "PASSWORD_MD5";
            public const string CodiceTipoPagamento = "CODICE_TIPO_PAGAMENTO";
        }

        public VerticalizzazionePagamentiEntraNext(string idComuneAlias, string software) : base(idComuneAlias, Constants.NomeVerticalizzazione, software)
        {

        }

        public string NomeVerticalizzazione
        {
            get { return GetString(Constants.NomeVerticalizzazione); }
            set { SetString(Constants.NomeVerticalizzazione, value); }
        }

        /// <summary>
        /// End point di riferimento relativamente al web service del sistema di pagamenti.
        /// </summary>
        public string UrlWs
        {
            get { return GetString(Constants.UrlWs); }
            set { SetString(Constants.UrlWs, value); }
        }

        /// <summary>
        /// Identificativo del connettore assegnato, deve essere comunicato dal cliente, serve per valorizzare l'oggetto IntestazioneFO che deve essere passato come argomento su tutte le chiamate.
        /// </summary>
        public string IdentificativoConnettore
        {
            get { return GetString(Constants.IdentificativoConnettore); }
            set { SetString(Constants.IdentificativoConnettore, value); }
        }

        /// <summary>
        /// Codice fiscale Ente sul quale lavorare, serve per valorizzare l'oggetto IntestazioneFO che deve essere passato come argomento su tutte le chiamate.
        /// </summary>
        public string CodiceFiscaleEnte
        {
            get { return GetString(Constants.CodiceFiscaleEnte); }
            set { SetString(Constants.CodiceFiscaleEnte, value); }
        }

        /// <summary>
        /// Parametro di riferimento che serve per effettuare la login al web service, deve essere comunicato dal cliente.
        /// </summary>
        public string Versione
        {
            get { return GetString(Constants.Versione); }
            set { SetString(Constants.Versione, value); }
        }

        /// <summary>
        /// Identificativo per l’accesso, parametro che serve per effettuare la login al web service, deve essere comunicato dal cliente.
        /// </summary>
        public string Identificativo
        {
            get { return GetString(Constants.Identificativo); }
            set { SetString(Constants.Identificativo, value); }
        }

        /// <summary>
        /// Username per l'accesso al web service, deve essere comunicato dal cliente.
        /// </summary>
        public string Username
        {
            get { return GetString(Constants.Username); }
            set { SetString(Constants.Username, value); }
        }

        /// <summary>
        /// Password di accesso al web service criptata in MD5 (deve essere valorizzato l'MD5 in questo parametro), deve essere comunicata dal cliente.
        /// </summary>
        public string PasswordMd5
        {
            get { return GetString(Constants.PasswordMd5); }
            set { SetString(Constants.PasswordMd5, value); }
        }

        /// <summary>
        /// Id del tipo pagamento da utilizzare per i pagamenti online
        /// </summary>
        public string CodiceTipoPagamento
        {
            get { return GetString(Constants.CodiceTipoPagamento); }
            set { SetString(Constants.CodiceTipoPagamento, value); }
        }
    }
}
