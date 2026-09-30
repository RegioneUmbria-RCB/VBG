using SIGePro.Manager.VerticalizzazioniBase;


namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione PROTOCOLLO_SIDUMBRIA il 14/01/2015 9.53.20
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// E' il protocollo gestito dalla Regione Umbria con intermediario webred.
    /// </summary>
    public partial class VerticalizzazioneProtocolloSidumbria : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_SIDUMBRIA";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloSidumbria(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune) { }

        public VerticalizzazioneProtocolloSidumbria()
        {

        }


        /// <summary>
        /// In questo parametro va indicato il codice presente nella MODALITA' di TRASMISSIONE che fa riferimento al destinatario per conoscenza, la tabella di riferimento è PROTOCOLLO_MODALITAINVIO. In questa tabella saranno presenti due valori che saranno poi associati ai destinatari per i protocolli in partenza (questo dato viene ignorato per i protocolli in entrata), se al destinatario viene associato lo stesso codice presente in questo parametro allora significherà che quel determinato destinatario, il protocollo, è per conoscenza.
        /// </summary>
        public string DestinatarioCc
        {
            get { return this.GetString("DESTINATARIO_CC"); }
            set { this.SetString("DESTINATARIO_CC", value); }
        }

        /// <summary>
        /// Il Service è uno dei due dati relativi all'autenticazione al web service (l'altro è il Token), deve essere fornito dal fornitore di protocollo, in alternativa dal cliente, sarebbe il codice servizio abilitato, in teoria una specie di Registro.
        /// </summary>
        public string Service
        {
            get { return this.GetString("SERVICE"); }
            set { this.SetString("SERVICE", value); }
        }

        /// <summary>
        /// Il Token è uno dei due dati relativi all'autenticazione al web service (l'altro è il Service), deve essere fornito dal fornitore di protocollo, in alternativa dal cliente, sarebbe il codice di autorizzazione.
        /// </summary>
        public string Token
        {
            get { return this.GetString("TOKEN"); }
            set { this.SetString("TOKEN", value); }
        }

        /// <summary>
        /// In questo parametro va indicato l'url del web service sidumbria, è possibile indicare anche l'indirizzo del wsdl.
        /// </summary>
        public string Url
        {
            get { return this.GetString("URL"); }
            set { this.SetString("URL", value); }
        }

        /// <summary>
        /// Questo parametro indica la versione del ws che è stato installato, al momento SID presenta due versioni, la 2.0 e la 4.0, nella seconda viene gestita anche la UORDestinatario, ossia l''ufficio destinatario di un protocollo in arrivo. Indicare quindi il valore "4.0" se la versione è la medesima altrimenti "2.0", se la versione non sarà specificata verrà presa in considerazione la prima, ossia la "2.0". Se verrà inserito un valore diverso da quelli specificati verrà sollevato un errore.
        /// </summary>
        public string Versione
        {
            get { return this.GetString("VERSIONE"); }
            set { this.SetString("VERSIONE", value); }
        }





        /// <summary>
        /// Valori 1 o altro. Se valorizzato a 1 tutto il sistema di invio files allegati al protocollo avverrà tramite FTP, dovranno quindi essere valorizzati anche i parametri FTP_URL, FTP_USERNAME e FTP_PASSWORD. Con qualsiasi valore diverso da 1 invece, il sistema si comporterà esattamente come prima, i file quindi saranno inviati come byte array direttamente nella request.
        /// </summary>
        public string UsaFtp
        {
            get { return this.GetString("USA_FTP"); }
            set { this.SetString("USA_FTP", value); }
        }

        /// <summary>
        /// FTP_URL', 'Valorizzare questo parametro con l''indirizzo ftp nel quale inserire i files allegati, sarà preso in considerazione solamente se il parametro USA_FTP è valorizzato a 1
        /// </summary>
        public string FtpUrl
        {
            get { return this.GetString("FTP_URL"); }
            set { this.SetString("FTP_URL", value); }
        }

        /// <summary>
        /// Inserire la credenziale di username del server ftp dove saranno inseriti gli allegati, preso in considerazione solamente se il parametro USA_FTP è valorizzato a 1
        /// </summary>
        public string FtpUsername
        {
            get { return this.GetString("FTP_USERNAME"); }
            set { this.SetString("FTP_USERNAME", value); }
        }

        /// <summary>
        /// Inserire la credenziale password del server ftp dove saranno inseriti gli allegati, preso in considerazione solamente se il parametro USA_FTP è valorizzato a 1
        /// </summary>
        public string FtpPassword
        {
            get { return this.GetString("FTP_PASSWORD"); }
            set { this.SetString("FTP_PASSWORD", value); }
        }

        /// <summary>
        /// Valore 1 o altro. Se valorizzato a 1 disabilita il controllo sull''esistenza dei files p7m durante la protocollazione automatica (su quella manuale non esiste alcun controllo in tal senso), e invia tutti i file presenti, invece che solo quelli con estensione p7m. Se invece non viene valorizzato o viene valorizzato diverso da 1, saranno inviati solo i file con estensione p7m, se non presenti verrà sollevato un errore (solo su protocollazione automatica da online).
        /// </summary>
        public string DisabilitaControlloP7m
        {
            get { return this.GetString("DISABILITA_CONTROLLO_P7M"); }
            set { this.SetString("DISABILITA_CONTROLLO_P7M", value); }
        }

    }
}
