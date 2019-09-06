using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
	public class CondizioniUscitaGestioneAnagrafiche : CondizioneUscitaStepBase, ICondizioneUscitaStep
	{
		ITipiSoggettoService _tipiSoggettoService;
		IAuthenticationDataResolver _authenticationDataResolver;
        IConfigurazione<ParametriLogin> _parametriLogin;

		internal bool FlagVerificaPecObbligatoria { get; set; }
		internal string MessaggioUtenteNonPresente { get; set; }
		internal bool VerificaPresenzaUtenteLoggato { get; set; }


        public CondizioniUscitaGestioneAnagrafiche(ITipiSoggettoService anagraficheService, IAuthenticationDataResolver authenticationDataResolver, IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService, IConfigurazione<ParametriLogin> parametriLogin)
			:base( idDomandaResolver , domandeService )
		{
			this._tipiSoggettoService = anagraficheService;
			this._authenticationDataResolver = authenticationDataResolver;
            this._parametriLogin = parametriLogin;

			this.VerificaPresenzaUtenteLoggato = true;
		}

		#region ICondizioneUscitaStep Members

		public bool Verificata()
		{
			DomandaOnline domanda = base.Domanda;
			var codiceFiscaleUtenteCorrente = _authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;
			var messaggioErroreUtenteNonPresente = String.Format(MessaggioUtenteNonPresente,
																	_authenticationDataResolver.DatiAutenticazione.DatiUtente.ToString(),
																	_authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale);

			var intervento = domanda.ReadInterface.AltriDati.Intervento;
			var codiceIntervento = intervento == null ? (int?)null : intervento.Codice;

			var regoleValidazione = new List<ValidazioneAnagraficheDomandaBase>
			{
				new TuttiISoggettiObbligatoriSonoPresentiSpecification( this._tipiSoggettoService, codiceIntervento ),
				new EsisteAlmenoUnSoggettoRichiedente(),
				new EsisteUnAnagraficaCollegataPerTuttiISoggettiCheLaRichiedono(),
				new AlmenoUnSoggettoHaUnIndirizzoPecSeRichiesta( this.FlagVerificaPecObbligatoria )				
			};

            var utenteAnonimo = new IsUtenteAnonimoSpecification(this._parametriLogin).IsSatisfiedBy(_authenticationDataResolver.DatiAutenticazione);

            if (!utenteAnonimo && VerificaPresenzaUtenteLoggato)
			{
				regoleValidazione.Add(new UtenteCorrentePresenteTraLeAnagrafiche(codiceFiscaleUtenteCorrente, messaggioErroreUtenteNonPresente));
			}

			foreach (var regola in regoleValidazione)
			{
				if (!regola.IsSatisfiedBy(domanda.ReadInterface.Anagrafiche))
					throw new StepException(regola.GetListaErrori());
			}

			return true;
		}

		#endregion
	}
}