using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using Ninject;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd
{
    public partial class RegistrazioneCompletata : BasePage
    {
        [Inject]
        public IConfigurazione<ParametriRegistrazione> _configurazioneRegistrazione { get; set; }

        ILog _log = LogManager.GetLogger(typeof(RegistrazioneCompletata));

        // Se si è verificato un errore durante la fase di registrazione dell'anagrafica 
        // il codice e la descrizione dell'errore vengono messi tra gli items del contesto http
        // dall'authenticationhelper che gestisce la registrazione. 
        // Le chiavi sono: 
        //  - errore-registrazione-codice
        //  - errore-registrazione-descrizione
        // Gli items persistono perché viene effettuato un server.transfer invece di un response.redirect
        public bool EsitoPositivo
        {
            get { return String.IsNullOrEmpty(HttpContext.Current.Items["errore-registrazione-codice"]?.ToString()); }
        }

        public string MessaggioErrore
        {
            get { return TraduciMessaggioErrore(); }
        }

        private string TraduciMessaggioErrore()
        {
            if (EsitoPositivo)
            {
                return string.Empty;
            }

            var codiceErrore = HttpContext.Current.Items["errore-registrazione-codice"].ToString();
            var descrizione = HttpContext.Current.Items["errore-registrazione-descrizione"].ToString();
            var errorRef = Guid.NewGuid().ToString();

            this._log.Error($"Errore durante la procedura di registrazione. Codice errore {codiceErrore} - {descrizione}, riferimento errore: {errorRef}");

            return $"<h3>{descrizione}</h3> Si è verificato un errore durante la procedura di registrazione. Contattare l'ente per assistenza (Codice errore: {errorRef})";
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var msg = _configurazioneRegistrazione.Parametri.MessaggioRegistrazioneCompletata;

                if (!String.IsNullOrEmpty(msg))
                    lblTesto.Text = msg;
            }
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/login.aspx", pb =>
            {
                pb.Add(new QsAliasComune(Request.QueryString));
                pb.Add(new QsSoftware(Request.QueryString));
            });

            Response.Redirect(url);
        }
    }
}
