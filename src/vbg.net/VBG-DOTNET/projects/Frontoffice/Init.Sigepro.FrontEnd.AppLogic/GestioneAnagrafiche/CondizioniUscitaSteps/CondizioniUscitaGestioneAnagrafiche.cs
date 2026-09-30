using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.CondizioniUscitaSteps
{
    public class CondizioniUscitaGestioneAnagrafiche : CondizioneUscitaGestioneAnagraficheBase
    {
        public IConfigurazione<ParametriPresentazioneDomanda> _parametriPresentazioneDomanda { get; set; }

        public CondizioniUscitaGestioneAnagrafiche(ITipiSoggettoService anagraficheService, IAuthenticationDataResolver authenticationDataResolver,
                                                                IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService,
                                                                IConfigurazione<ParametriLogin> parametriLogin, IConfigurazione<ParametriPresentazioneDomanda> parametriPresentazioneDomanda) : base(anagraficheService, authenticationDataResolver,
                                                                                                                        idDomandaResolver, domandeService, parametriLogin)
        {
            this._parametriPresentazioneDomanda = parametriPresentazioneDomanda;

        }

        public override bool Verificata()
        {
            var intervento = base.Domanda.ReadInterface.AltriDati.Intervento;
            var codiceIntervento = intervento == null ? (int?)null : intervento.Codice;

            var regoleValidazione = new List<ValidazioneAnagraficheDomandaBase>
            {
                new TuttiISoggettiObbligatoriSonoPresentiSpecification( this._tipiSoggettoService, codiceIntervento ),
                new EsisteAlmenoUnSoggettoRichiedente( this._parametriPresentazioneDomanda.Parametri.RichiedenteSoloPersoneFisiche ),
                new EsisteUnAnagraficaCollegataPerTuttiISoggettiCheLaRichiedono(),
                new AlmenoUnSoggettoHaUnIndirizzoPecSeRichiesta( this.FlagVerificaPecObbligatoria ),
                new TutteLeTipologieDiSoggettoSonoCensiteEValide()
            };

            return base.Verificata(regoleValidazione);
        }
    }
}
