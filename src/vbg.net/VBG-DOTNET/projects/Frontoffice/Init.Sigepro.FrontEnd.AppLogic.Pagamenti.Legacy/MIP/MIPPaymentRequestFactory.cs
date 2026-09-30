using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using VBG.Pagamenti.Legacy;
using VBG.Pagamenti.Legacy.MIP;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public class MIPPaymentRequestFactory : IMIPPaymentRequestFactory
    {
        private static class Constants
        {
            public const string SpecializzazioneSettingsKey = "MIPPaymentRequestFactory.SpecializzazioneMIP";
        }

        private readonly PagamentiSettings _settings;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IAppConfigurationReader _appConfigurationReader;
        private readonly IResolveUrl _resolveUrl;

        public MIPPaymentRequestFactory(IPagamentiSettingsReader settingsReader, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAuthenticationDataResolver authenticationDataResolver, IAppConfigurationReader appConfigurationReader, IResolveUrl resolveUrl)
        {
            this._settings = settingsReader.GetSettings();
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._authenticationDataResolver = authenticationDataResolver;
            this._appConfigurationReader = appConfigurationReader;
            this._resolveUrl = resolveUrl;
        }

        public PaymentRequest Create(IniziaPagamentoRequest request)
        {
            if (this._appConfigurationReader.GetSetting(Constants.SpecializzazioneSettingsKey) == "siena")
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(request.RiferimentiDomanda.IdDomanda);
                var utenteLogin = this._authenticationDataResolver.DatiAutenticazione.DatiUtente;
                var richiedenteDomanda = domanda.ReadInterface.Anagrafiche.GetRichiedente();

                var richiestaSiena = new PaymentRequest(this._settings, request, this._resolveUrl);

                richiestaSiena.UserDataExt = new UserDataExtType
                {
                    UtenteLogin = new UtenteLoginType
                    {
                        IdentificativoUtente = utenteLogin.Codicefiscale,
                        TipoIdentificativo = utenteLogin.Tipoanagrafe,
                        Cognome = utenteLogin.Nominativo,
                        Nome = utenteLogin.Nome
                    },
                    UtenteMandante = new UtenteType
                    {
                        IdentificativoUtente = richiedenteDomanda.Codicefiscale,
                        TipoIdentificativo = richiedenteDomanda.TipoPersona == GestionePresentazioneDomanda.GestioneAnagrafiche.TipoPersonaEnum.Fisica ? "F" : "G",
                        Cognome = richiedenteDomanda.Nominativo,
                        Nome = richiedenteDomanda.Nome
                    }
                };

                richiestaSiena.ServiceData = new PaymentRequestServiceData
                {
                    IDServizio = this._settings.IDServizio,
                    AnnoDocumento = DateTime.Now.Year.ToString(),
                    NumeroDocumento = domanda.ReadInterface.AltriDati.IdentificativoDomanda,
                    DescrizioneDocumento = $"Pagamento oneri per la pratica {domanda.ReadInterface.AltriDati.IdentificativoDomanda}"
                };


                return richiestaSiena;
            }

            return new PaymentRequest(this._settings, request, this._resolveUrl);
        }
    }
}
