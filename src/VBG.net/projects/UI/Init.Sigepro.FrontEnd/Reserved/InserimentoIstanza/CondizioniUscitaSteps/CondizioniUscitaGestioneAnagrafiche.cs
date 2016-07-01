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

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
	public class CondizioniUscitaGestioneAnagrafiche : CondizioneUscitaStepBase, ICondizioneUscitaStep
	{
		ITipiSoggettoService _tipiSoggettoService;
		IAuthenticationDataResolver _authenticationDataResolver;

		internal bool FlagVerificaPecObbligatoria { get; set; }
		internal string MessaggioUtenteNonPresente { get; set; }
		internal bool VerificaPresenzaUtenteLoggato { get; set; }


		public CondizioniUscitaGestioneAnagrafiche(ITipiSoggettoService anagraficheService, IAuthenticationDataResolver authenticationDataResolver, IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService)
			:base( idDomandaResolver , domandeService )
		{
			this._tipiSoggettoService = anagraficheService;
			this._authenticationDataResolver = authenticationDataResolver;

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

			if (VerificaPresenzaUtenteLoggato)
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