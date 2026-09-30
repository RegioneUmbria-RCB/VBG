using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System;
using System.Linq;
using System.Threading.Tasks;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche
{
    public class OnceOnlyAnagraficheService : IOnceOnlyAnagraficheService
    {
        private readonly IConfigurazione<ParametriOnceOnly> _configurazione;
        private readonly AnagraficheOnceOnlyClient _anagraficheOnceOnlyClient;
        private readonly ISoftwareResolver _softwareResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public bool IsOnceOnlyAttivo => this._configurazione.Parametri.AttivaCompilazioneOnceOnly;

        public OnceOnlyAnagraficheService(IConfigurazione<ParametriOnceOnly> configurazione, AnagraficheOnceOnlyClient anagraficheOnceOnlyClient, ISoftwareResolver softwareResolver, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._configurazione = configurazione;
            this._anagraficheOnceOnlyClient = anagraficheOnceOnlyClient;
            this._softwareResolver = softwareResolver;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        public async Task<Anagrafe> TrovaAnagraficaByCodiceFiscaleAsync(TipoPersonaEnum tipoPersona, string codiceFiscale)
        {
            return await this._anagraficheOnceOnlyClient.TrovaAnagraficaByCodiceFiscaleAsync(tipoPersona, codiceFiscale, this._softwareResolver.Software);
        }

        public async Task SalvaSoggettiDomandaAsync(IDomandaOnlineReadInterface domanda)
        {
            var cfRichiedente = this._authenticationDataResolver.DatiAutenticazione?.DatiUtente?.Codicefiscale?.ToUpper() ?? "";

            if (string.IsNullOrEmpty(cfRichiedente))
            {
                throw new InvalidOperationException("Impossibile recuperare il cf dell'utente corrente");
            }

            var anagraficheDomanda = domanda.Anagrafiche.Anagrafiche
                                            .Where(x => !String.IsNullOrEmpty(x.CodiceFiscaleOPartitaIva) && !x.Codicefiscale.Equals(cfRichiedente, StringComparison.InvariantCultureIgnoreCase))
                                            .Select(x => new AggiungiARubricaRequest
                                            {
                                                CodiceFiscale = x.CodiceFiscaleOPartitaIva,
                                                NomeCompleto = x.NomeEsteso,
                                                TipoAnagrafe = x.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G"
                                            })
                                            .ToArray();

            if (anagraficheDomanda.Count() == 0)
            {
                return;
            }

            await this._anagraficheOnceOnlyClient.SalvaAnagrafichePerUtenteCorrenteAsync(anagraficheDomanda);
        }

        public async Task<ElementoRubricaOnceOnly[]> GetAnagraficheInRubricaAsync(TipoPersonaEnum tipoPersona)
        {
            return await this._anagraficheOnceOnlyClient.GetAnagraficheInRubricaAsync(tipoPersona);
        }
    }
}
