using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.AutorizzazioniTransitiService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public class AutorizzazioniTransitiProxy : IAutorizzazioniTransitiProxy
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _config;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;

        public AutorizzazioniTransitiProxy(IConfigurazione<ParametriSigeproSecurity> config, IAliasSoftwareResolver aliasResolver, ITokenApplicazioneService tokenApplicazioneService)
        {
            _config = config;
            _aliasSoftwareResolver = aliasResolver;
            _tokenApplicazioneService = tokenApplicazioneService;
        }

        public AutorizzazioneTransito TrovaAutorizzazione(string codiceFiscale, string partitaIva, string numeroAutorizzazione, DateTime dataAutorizzazione)
        {
            return CallService(ws =>
            {
                var token = this._tokenApplicazioneService.GetToken();
                var aut = ws.RicercaAutorizzazioniAccessi(new RicercaAutorizzazioniAccessiRequest
                {
                    cfImpresa = codiceFiscale,
                    pivaImpresa = partitaIva,
                    dataAutorizzazione = dataAutorizzazione,
                    numeroAutorizzazione = numeroAutorizzazione,
                    software = this._aliasSoftwareResolver.Software,
                    token = token
                });

                return new AutorizzazioneTransito(aut);
            });
        }

        private T CallService<T>(Func<AutorizzazioniAccessiClient, T> callback)
        {
            var endpoint = new EndpointAddress(this._config.Parametri.UrlServizioAutorizzazioniTransiti);
            var binding = new BasicHttpBinding("defaultServiceBinding");    // IL web service utilizza la codifica MTOM

            using (var ws = new AutorizzazioniAccessiClient(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    ws.Abort();
                    throw;
                }
            }
        }
    }
}
