using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Exceptions;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
    public class CondizioneUscitaGestioneAnagraficheBase : CondizioneUscitaStepBase
    {
        public ITipiSoggettoService _tipiSoggettoService { get; set; }
        public IAuthenticationDataResolver _authenticationDataResolver { get; set; }
        public IConfigurazione<ParametriLogin> _parametriLogin { get; set; }

        public bool FlagVerificaPecObbligatoria { get; set; }
        public string MessaggioUtenteNonPresente { get; set; }
        public bool VerificaPresenzaUtenteLoggato { get; set; }

        public CondizioneUscitaGestioneAnagraficheBase(ITipiSoggettoService anagraficheService, IAuthenticationDataResolver authenticationDataResolver, IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService, IConfigurazione<ParametriLogin> parametriLogin)
    : base(idDomandaResolver, domandeService)
        {
            this._tipiSoggettoService = anagraficheService;
            this._authenticationDataResolver = authenticationDataResolver;
            this._parametriLogin = parametriLogin;

            this.VerificaPresenzaUtenteLoggato = true;
        }

        public virtual bool Verificata()
        {
            //implementare l'override nelle classi di estensione
            return true;
        }

        protected bool Verificata(List<ValidazioneAnagraficheDomandaBase> regoleValidazione)
        {
            var codiceFiscaleUtenteCorrente = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;
            var messaggioErroreUtenteNonPresente = String.Format(this.MessaggioUtenteNonPresente,
                                                                    this._authenticationDataResolver.DatiAutenticazione.DatiUtente.ToString(),
                                                                    this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale);

            var utenteAnonimo = new IsUtenteAnonimoSpecification(this._parametriLogin).IsSatisfiedBy(this._authenticationDataResolver.DatiAutenticazione);

            if (!utenteAnonimo && this.VerificaPresenzaUtenteLoggato)
            {
                regoleValidazione.Add(new UtenteCorrentePresenteTraLeAnagrafiche(codiceFiscaleUtenteCorrente, messaggioErroreUtenteNonPresente));
            }

            foreach (var regola in regoleValidazione)
            {
                if (!regola.IsSatisfiedBy(base.Domanda.ReadInterface.Anagrafiche))
                    throw new StepException(regola.GetListaErrori());
            }

            return true;
        }
    }
}