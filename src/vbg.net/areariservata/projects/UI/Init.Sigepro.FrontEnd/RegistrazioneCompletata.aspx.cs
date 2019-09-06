using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;

namespace Init.Sigepro.FrontEnd
{
	public partial class RegistrazioneCompletata : BasePage
	{
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }

		[Inject]
		public IConfigurazione<ParametriAspetto> _configurazioneAspetto { get; set; }
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
				lblNomeComune2.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(Software).DENOMINAZIONE;
				var msg = _configurazioneRegistrazione.Parametri.MessaggioRegistrazioneCompletata;

				if (!String.IsNullOrEmpty(msg))
					lblTesto.Text = msg;
			}
		}
	}
}
