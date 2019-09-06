using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneProtocolloCivilia : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_CIVILIA";

        public VerticalizzazioneProtocolloCivilia()
        {

        }

        public VerticalizzazioneProtocolloCivilia(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Endpoint relativo all''autenticazione oauth 2.0 da utilizzare per ottenere il token da usare poi per le chiamate al web service rest.
        /// </summary>
        public string UrlOauth
        {
            get { return GetString("URL_OAUTH"); }
            set { SetString("URL_OAUTH", value); }
        }

        /// <summary>
        /// Endpoint del web service rest
        /// </summary>
        public string UrlWs
        {
            get { return GetString("URL_WS"); }
            set { SetString("URL_WS", value); }
        }

        /// <summary>
        /// Parametro client_id da passare all''endpoint oauth 2.0
        /// </summary>
        public string ClientId
        {
            get { return GetString("CLIENT_ID"); }
            set { SetString("CLIENT_ID", value); }
        }

        /// <summary>
        /// Parametro secret da passare all''endpoint oauth 2.0
        /// </summary>
        public string Secret
        {
            get { return GetString("SECRET"); }
            set { SetString("SECRET", value); }
        }

        /// <summary>
        /// Indicare in questo parametro il valore relativo al codice AOO del protocollo, che sarà poi inserito nel parametro IdCodiceAOO della richieta json.
        /// </summary>
        public string CodiceAoo
        {
            get { return GetString("CODICEAOO"); }
            set { SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// Endpoint del servizio rest adibito a contattare il servizio OAUTH2, si è reso necessario lo sviluppo di un servizio rest adibito solamente a staccare il token, in quanto le classi da utilizzare hanno bisogno di una versione del framework superiore a quella di sigepro.net che non può essere aggiornato per via dell''utilizzo di classi che si interfacciano con SqlServer2003 (ancora utilizzato).
        /// </summary>
        public string UrlWrapperServiceOAuth
        {
            get { return GetString("URL_SERVICE_OAUTH"); }
            set { SetString("URL_SERVICE_OAUTH", value); }
        }

        /// <summary>
        /// Indicare la url che identifica la risorsa che contiene il token richiesto, in genere è sufficiente indicare la parte iniziale del ws indicato nel parametro URL_WS, in pratica invece di http://api.civilianextdev.it/Protocollo/Protocollo/Protocolla inserire http://api.civilianext.dev.it
        /// </summary>
        public string UrlWsResource
        {
            get { return GetString("URL_WSRESOURCE"); }
            set { SetString("URL_WSRESOURCE", value); }
        }
    }
}
