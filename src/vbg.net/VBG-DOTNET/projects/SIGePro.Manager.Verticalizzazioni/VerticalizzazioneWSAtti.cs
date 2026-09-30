
using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneWSAtti : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "WS_ATTI";
        private const string OGGETTOTESTOTIPO = "OGGETTO_TESTOTIPO";
        private const string CODICETRATTAMENTO = "CODICE_TRATTAMENTO";
        private const string CODICEPROPONENTE = "CODICE_PROPONENTE";
        private const string CODICEDIRIGENTE = "CODICE_DIRIGENTE";
        private const string CLASSIFICA = "CLASSIFICA";
        private const string UTENTE = "UTENTE";
        private const string TIPOCONNETTORE = "TIPO_CONNETTORE";
        private const string NOTEINTEGRATIVE = "NOTE_INTEGRATIVE";
        private const string RUOLO = "RUOLO";
        private const string URL = "URL";
        private const string URL_FIRMATARI = "URL_FIRMATARI";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneWSAtti()
        {

        }
        public VerticalizzazioneWSAtti(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }


        /// <summary>
        /// Codice della classifica
        /// </summary>
        public string Classifica => this.GetString(CLASSIFICA);

        /// <summary>
        /// Codice che identifica il dirigente nel sistema esterno; solitamente coincide con il firmatario
        /// </summary>
        public string CodiceDirigente => this.GetString(CODICEDIRIGENTE);

        /// <summary>
        /// Codice che identifica l''ufficio proponente nel sistema esterno
        /// </summary>
        public string CodiceProponente => this.GetString(CODICEPROPONENTE);

        /// <summary>
        /// Contiene il riferimento al testo tipo che verrà utilizzato per recuperare l''oggetto della determina,ordinanza,.....
        /// </summary>
        public int? CodiceTestoTipoOggetto => this.GetInt(OGGETTOTESTOTIPO);

        /// <summary>
        /// Codice del tipo di iter che viene passato al sistema esterno per identificare l''iter che l''atto seguirà nel gestionale esterno
        /// </summary>
        public string CodiceTrattamento => this.GetString(CODICETRATTAMENTO);

        /// <summary>
        /// URL dei servizi web del sistema esterno che gestisce gli atti (determina,ordinanza,...)
        /// </summary>
        public string Url => this.GetString(URL);

        /// <summary>
        /// URL dei servizi web del sistema esterno che ritorna l'elenco dei firmatari
        /// </summary>
        public string UrlFirmatari => this.GetString(URL_FIRMATARI);

        /// <summary>
        /// Utente applicativo utilizzato per la connessione ai servizi
        /// </summary>
        public string Utente => this.GetString(UTENTE);

        public string Ruolo => this.GetString(RUOLO);

        public string TipoConnettore => this.GetString(TIPOCONNETTORE);

        /// <summary>
        /// Utilizzato qualora debbano essere aggiunte delle note fisse, sistematicamente ad ogni richiesta nuovo atto
        /// </summary>
        public string NoteIntegrative => this.GetString(NOTEINTEGRATIVE);
    }
}
