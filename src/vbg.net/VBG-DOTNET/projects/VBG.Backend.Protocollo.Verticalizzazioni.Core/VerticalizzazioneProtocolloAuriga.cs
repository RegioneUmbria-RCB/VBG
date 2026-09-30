using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{ 
    public class VerticalizzazioneProtocolloAuriga : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_AURIGA";


        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;


        public VerticalizzazioneProtocolloAuriga()
        {

        }

        public VerticalizzazioneProtocolloAuriga(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        /// <summary>
        /// Endpoint del web service Auriga; nell'endpoint bisogna sostituire il servizio con {servicename} es: http://aurigatest.comune.genova.it:8080/AurigaBusiness/soap/{servicename}?wsdl
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// End point del web service Java che fa da proxy verso Auriga
        /// </summary>
        public string ProxyUrl
        {
            get { return this.GetString("PROXY_URL"); }
            set { this.SetString("PROXY_URL", value); }
        }


        /// <summary>
        /// Username relativo alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo.
        /// </summary>
        public string Username
        {
            get { return this.GetString("USERNAME"); }
            set { this.SetString("USERNAME", value); }

        }

        /// <summary>
        /// Password relativa alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo.
        /// </summary>
        public string Password
        {
            get { return this.GetString("PASSWORD"); }
            set { this.SetString("PASSWORD", value); }
        }

        /// <summary>
        /// Codice identificativo dell’applicazione che chiama il WS (obbligatorio): se la chiamata è dall’interno dello stesso catalogo servizi va valorizzata come “AURIGA”
        /// </summary>
        public string CodApplicazione
        {
            get { return this.GetString("CODAPPLICAZIONE"); }
            set { this.SetString("CODAPPLICAZIONE", value); }
        }

        /// <summary>
        /// Codice identificativo dell’istanza dell’applicazione esterna che chiama il WS (se applicazione multi-istanza)
        /// </summary>
        public string IstanzaApplicazione
        {
            get { return this.GetString("ISTANZAAPPLICAZIONE"); }
            set { this.SetString("ISTANZAAPPLICAZIONE", value); }
        }

        public string Versione
        {
            get { return this.GetString("VERSIONE"); }
            set { this.SetString("VERSIONE", value); }
        }
    }
}
