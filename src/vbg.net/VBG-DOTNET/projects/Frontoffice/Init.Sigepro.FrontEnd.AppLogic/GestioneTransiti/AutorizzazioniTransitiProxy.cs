using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.AutorizzazioniTransitiService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using VBG.Shared.Infrastructure.ServiceModel;
using System;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public class AutorizzazioniTransitiProxy : IAutorizzazioniTransitiProxy
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _config;
        private readonly ISoftwareResolver _aliasSoftwareResolver;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IBindingFactory _bindingFactory;
        public AutorizzazioniTransitiProxy(IConfigurazione<ParametriSigeproSecurity> config, ISoftwareResolver aliasResolver, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory)
        {
            this._config = config;
            this._aliasSoftwareResolver = aliasResolver;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._bindingFactory = bindingFactory;
        }

        public AutorizzazioneTransito TrovaAutorizzazione(string codiceFiscale, string partitaIva, string numeroAutorizzazione, DateTime dataAutorizzazione)
        {
            return this.CallService(ws =>
            {
                var token = this._tokenApplicazioneService.GetToken();
                var aut = ws.RicercaAutorizzazioniAccessi(new RicercaAutorizzazioniAccessiRequest{cfImpresa = codiceFiscale, pivaImpresa = partitaIva, dataAutorizzazione = dataAutorizzazione, numeroAutorizzazione = numeroAutorizzazione, software = this._aliasSoftwareResolver.Software, token = token});
                return new AutorizzazioneTransito(aut);
            });
        }

        private T CallService<T>(Func<AutorizzazioniAccessiClient, T> callback)
        {
            var endpoint = new EndpointAddress(this._config.Parametri.UrlServizioAutorizzazioniTransiti);
            var binding = this._bindingFactory.CreateAndConfigure("defaultServiceBinding"); // IL web service utilizza la codifica MTOM
            using (var ws = new AutorizzazioniAccessiClient(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception)
                {
                    ws.Abort();
                    throw;
                }
            }
        }
    }
}