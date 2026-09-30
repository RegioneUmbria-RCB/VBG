using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CondizioniUscitaSteps
{
    public class CondizioniUscitaGestioneAnagraficheSemplificata : CondizioneUscitaGestioneAnagraficheBase
    {
        private readonly bool _richiedenteSoloPersoneFisiche;
        public CondizioniUscitaGestioneAnagraficheSemplificata(ITipiSoggettoService anagraficheService, IAuthenticationDataResolver authenticationDataResolver,
                                                                IIdDomandaResolver idDomandaResolver, DomandeOnlineService domandeService,
                                                                IConfigurazione<ParametriLogin> parametriLogin, IConfigurazione<ParametriPresentazioneDomanda> parametriPresentazioneDomanda) : base(anagraficheService, authenticationDataResolver,
                                                                                                                        idDomandaResolver, domandeService, parametriLogin)
        {
            this._richiedenteSoloPersoneFisiche = parametriPresentazioneDomanda.Parametri.RichiedenteSoloPersoneFisiche;
        }

        public override bool Verificata()
        {
            var intervento = base.Domanda.ReadInterface.AltriDati.Intervento;
            var codiceIntervento = intervento == null ? (int?)null : intervento.Codice;

            var regoleValidazione = new List<ValidazioneAnagraficheDomandaBase>
            {
            new TuttiISoggettiObbligatoriSonoPresentiSpecification( base._tipiSoggettoService, codiceIntervento ),
            new EsisteAlmenoUnSoggettoRichiedente( this._richiedenteSoloPersoneFisiche ),
            new EsisteUnAnagraficaCollegataPerTuttiISoggettiCheLaRichiedono(),
            new EsisteUnRappresentantePerTutteLePersoneGiuridicheIndicate(base._tipiSoggettoService ),
            new AlmenoUnSoggettoHaUnIndirizzoPecSeRichiesta( this.FlagVerificaPecObbligatoria )
            };

            return base.Verificata(regoleValidazione);
        }

    }
}