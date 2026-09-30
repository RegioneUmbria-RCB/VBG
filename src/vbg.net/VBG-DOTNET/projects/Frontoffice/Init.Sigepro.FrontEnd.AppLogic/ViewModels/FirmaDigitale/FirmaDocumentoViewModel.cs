using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.ViewModels.FirmaDigitale
{
    public class FirmaDocumentoViewModel
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(FirmaDocumentoViewModel));
        private readonly IOggettiService _oggettiService;
        private readonly IVerificaFirmaDigitaleService _firmaDigitaleService;
        private readonly AllegatiService _allegatiService;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IAliasResolver _aliasResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public FirmaDocumentoViewModel(IAliasResolver aliasResolver, IAuthenticationDataResolver authenticationDataResolver, IOggettiService oggettiService, IVerificaFirmaDigitaleService firmaDigitaleService, AllegatiService allegatiService, ITokenApplicazioneService tokenApplicazioneService)
        {
            this._oggettiService = oggettiService;
            this._firmaDigitaleService = firmaDigitaleService;
            this._allegatiService = allegatiService;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        public string GetTokenApplicazione()
        {
            return this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);
        }

        public void AggiornaStatoFirma(int idDomanda, int codiceOggetto, string nomeFile)
        {
            // Se la verifica ha successo imposto il flag "FirmatoDigitalmente" dell'allegato della domanda
            // e aggiorna il nome file
            this._allegatiService.ModificaNomeFileEFlagFirmaDaCodiceOggetto(idDomanda, codiceOggetto, nomeFile, true);
        }

        public string GetNomeFile(int codiceOggetto)
        {
            return this._oggettiService.GetNomeFile(codiceOggetto);
        }
    }
}
