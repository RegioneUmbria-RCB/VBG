using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    /// <summary>
    /// Se attivo consente la protocollazione tramite il componente che si integra con il protocollo della ditta KIBERNETES.
    /// </summary>
    public partial class VerticalizzazioneProtocolloKibernetes : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_KIBERNETES";

        private const string UFFICIO_PROTOCOLLANTE = "UFFICIO_PROTOCOLLANTE";
        private const string VERSIONE = "VERSIONE";
        private const string URL = "URL";
        private const string USERNAME = "USERNAME";
        private const string PASSWORD = "PASSWORD";
        private const string CODICEISTAT = "CODICEISTAT";
        private const string USA_RUOLO_ENTRATA = "USA_RUOLO_ENTRATA";
        private const string USA_RUOLO_USCITA = "USA_RUOLO_USCITA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloKibernetes()
        {

        }

        public VerticalizzazioneProtocolloKibernetes(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        /// <summary>
        /// Indicare l'ufficio UO che si occupa di protocollare, da non confondere con l'ufficio assegnatario che viene valorizzato con l'UO e RUOLO delle amministrazioni, anche se gli archivi da dove recuperare questo dato sono sempre gli stessi, ed è possibile interrogarli tramite il metodo search4UO.
        /// </summary>
        public string UfficioProtocollante => this.GetString(UFFICIO_PROTOCOLLANTE);

        /// <summary>
        /// Indicare l'indirizzo endpoint dove è installato il web service Kibernetes.
        /// </summary>
        public string Url => this.GetString(URL);

        /// <summary>
        /// Indicare in questo parametro lo username di accesso al web service, tenendo presente che i web service necessitano di autenticazione preemptive.
        /// </summary>
        public string Username => this.GetString(USERNAME);

        /// <summary>
        /// Indicare in questo parametro la password di accesso al web service (strettamente legata al parametro USERNAME chiaramente), tenendo presente che i web service necessitano di autenticazione preemptive.
        /// </summary>
        public string Password => this.GetString(PASSWORD);


        /// <summary>
        /// E' il codice con cui viene identificato l'ente sul sistema di protocollo e va indicato da tutti i metodi del web service, in genere viene valorizzato proprio con il codice istat del comune interessato, tuttavia si può ricavare invocando il metodo GetElencoIstat del web service.
        /// </summary>
        public int? CodiceIstat => this.GetInt(CODICEISTAT);

        public int? Versione => this.GetInt(VERSIONE);

        /// <summary>
        /// Usato nella protocollazione in entrata come valore per destinatari amministrazione. 
        /// Default: 0 (verràutilizzato il valore del parametro USERNAME)
        /// Se impostato a 1 utilizza il ruolo nei Parametri Protocollo dell'amministrazione relativa al parametro CODICEAMMINISTRAZIONE del PROTOCOLLO_ATTIVO
        /// </summary>
        public bool UsaRuoloInEntrata => this.GetBool(USA_RUOLO_ENTRATA);
        /// <summary>
        /// Usato nella protocollazione in uscita come valore per destinatari amministrazione.
        /// Default: 0 (verràutilizzato il valore del parametro USERNAME)
        /// Se impostato a 1 utilizza il ruolo nei Parametri Protocollo dell'amministrazione relativa al parametro CODICEAMMINISTRAZIONE del PROTOCOLLO_ATTIVO
        /// </summary>
        public bool UsaRuoloInUscita => this.GetBool(USA_RUOLO_USCITA);
    }
}
