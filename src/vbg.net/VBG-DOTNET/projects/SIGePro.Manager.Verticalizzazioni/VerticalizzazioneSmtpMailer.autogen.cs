using SIGePro.Manager.VerticalizzazioniBase;

namespace SIGePro.Manager.Verticalizzazioni
{
    /**************************************************************************************************************************************
    *
    * Classe generata automaticamente dalla verticalizzazione SMTP_MAILER il 26/08/2014 17.23.45
    * NON MODIFICARE DIRETTAMENTE!!!
    *
    ***************************************************************************************************************************************/


    /// <summary>
    /// Se attiva, consente di inviare e-mail utilizzando il compontente SMTPMailer
    /// </summary>
    public partial class VerticalizzazioneSmtpMailer : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "SMTP_MAILER";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;
        public VerticalizzazioneSmtpMailer()
        {

        }

        public VerticalizzazioneSmtpMailer(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }


        /// <summary>
        /// Utente per stabilire una connessione con il server SMTP ( deve essere specificato se il parametro AUTH_MODE è diverso da "None" )
        /// </summary>
        public string Loginname
        {
            get { return this.GetString("LOGINNAME"); }
            set { this.SetString("LOGINNAME", value); }
        }

        /// <summary>
        /// Password per stabilire una connessione con il server SMTP ( deve essere specificato se il parametro AUTH_MODE è diverso da "None" )
        /// </summary>
        public string Loginpass
        {
            get { return this.GetString("LOGINPASS"); }
            set { this.SetString("LOGINPASS", value); }
        }

        /// <summary>
        /// E' la porta di comunicazione del SMTPMailer.
        /// </summary>
        public string Port
        {
            get { return this.GetString("PORT"); }
            set { this.SetString("PORT", value); }
        }

        /// <summary>
        /// Accetta solo 1 o 0 e specifica se il corpo del messaggio deve essere inviato come Html oppure no.
        /// </summary>
        public string Sendashtml
        {
            get { return this.GetString("SENDASHTML"); }
            set { this.SetString("SENDASHTML", value); }
        }

        /// <summary>
        /// Se impostato a 1 in fase di invio di mail certificate accetta anche certificati non validi
        /// </summary>
        public string SslAcceptinvalidcertificates
        {
            get { return this.GetString("SSL_ACCEPTINVALIDCERTIFICATES"); }
            set { this.SetString("SSL_ACCEPTINVALIDCERTIFICATES", value); }
        }

        /// <summary>
        /// Specifica se utilizzare l'autenticazione per l'invio delle mail (valori: 1-Usa autenticazione 0-Non usa autenticazione)
        /// </summary>
        public string Useauthentication
        {
            get { return this.GetString("USEAUTHENTICATION"); }
            set { this.SetString("USEAUTHENTICATION", value); }
        }

        /// <summary>
        /// Specifica se utilizzare ssl per inviare le mail (valori: 1-Usa ssl , 0-Non usa ssl)
        /// </summary>
        public string Usessl
        {
            get { return this.GetString("USESSL"); }
            set { this.SetString("USESSL", value); }
        }

        /// <summary>
        /// Url del Web service utilizzato per l'invio delle mail
        /// </summary>
        public string WsSmtpMailUrl
        {
            get { return this.GetString("WS_SMTP_MAIL_URL"); }
            set { this.SetString("WS_SMTP_MAIL_URL", value); }
        }


    }
}
