using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloCivilia : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_CIVILIA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

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
            get { return this.GetString("URL_OAUTH"); }
            set { this.SetString("URL_OAUTH", value); }
        }

        /// <summary>
        /// Endpoint del web service rest
        /// </summary>
        public string UrlWs
        {
            get { return this.GetString("URL_WS"); }
            set { this.SetString("URL_WS", value); }
        }

        /// <summary>
        /// Parametro client_id da passare all''endpoint oauth 2.0
        /// </summary>
        public string ClientId
        {
            get { return this.GetString("CLIENT_ID"); }
            set { this.SetString("CLIENT_ID", value); }
        }

        /// <summary>
        /// Parametro secret da passare all''endpoint oauth 2.0
        /// </summary>
        public string Secret
        {
            get { return this.GetString("SECRET"); }
            set { this.SetString("SECRET", value); }
        }

        /// <summary>
        /// Indicare in questo parametro il valore relativo al codice AOO del protocollo, che sarà poi inserito nel parametro IdCodiceAOO della richieta json.
        /// </summary>
        public string CodiceAoo
        {
            get { return this.GetString("CODICEAOO"); }
            set { this.SetString("CODICEAOO", value); }
        }

        /// <summary>
        /// Endpoint del servizio rest adibito a contattare il servizio OAUTH2, si è reso necessario lo sviluppo di un servizio rest adibito solamente a staccare il token, in quanto le classi da utilizzare hanno bisogno di una versione del framework superiore a quella di sigepro.net che non può essere aggiornato per via dell''utilizzo di classi che si interfacciano con SqlServer2003 (ancora utilizzato).
        /// </summary>
        public string UrlWrapperServiceOAuth
        {
            get { return this.GetString("URL_SERVICE_OAUTH"); }
            set { this.SetString("URL_SERVICE_OAUTH", value); }
        }

        /// <summary>
        /// Indicare la url che identifica la risorsa che contiene il token richiesto, in genere è sufficiente indicare la parte iniziale del ws indicato nel parametro URL_WS, in pratica invece di http://api.civilianextdev.it/Protocollo/Protocollo/Protocolla inserire http://api.civilianext.dev.it
        /// </summary>
        public string UrlWsResource
        {
            get { return this.GetString("URL_WSRESOURCE"); }
            set { this.SetString("URL_WSRESOURCE", value); }
        }

        /// <summary>
        /// In questo parametro va indicato che tipo di richiesta deve essere eseguita, se in GET o in POST, i parametri accettati sono, appunto GET e POST (scritto in maiuscolo). Se sarà indicato qualsiasi altro valore o se non sarà inserito alcun valore, o se il parametro non sarà presente, la richiesta sarà fatta in POST.
        /// </summary>
        public string TipoRichiesta
        {
            get { return this.GetString("TIPO_RICHIESTA"); }
            set { this.SetString("TIPO_RICHIESTA", value); }
        }
    }
}
