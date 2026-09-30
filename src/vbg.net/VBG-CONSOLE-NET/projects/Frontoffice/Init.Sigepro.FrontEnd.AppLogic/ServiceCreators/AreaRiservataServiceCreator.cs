using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
// using Init.Sigepro.FrontEnd.AppLogic.Configuration;

using log4net;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    internal class AreaRiservataServiceCreator
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _config;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IAliasResolver _aliasResolver;

        public AreaRiservataServiceCreator(IAliasResolver aliasResolver, IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
        {
            Condition.Requires(config, "config").IsNotNull();
            Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

            this._config = config;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
        }

        public ServiceInstance<AreaRiservataServiceSoapClient> CreateClient()
        {
            return this.CreateClient(this._aliasResolver.AliasComune);
        }

        public ServiceInstance<AreaRiservataServiceSoapClient> CreateClient(string aliasComune)
        {
            ILog log = LogManager.GetLogger(typeof(AreaRiservataServiceCreator));

            log.DebugFormat("Inizializzazione del web service di gestione dati dell'area riservata all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);


            var endPoint = new EndpointAddress(this._config.Parametri.UrlAreaRiservataService);
            var binding = new BasicHttpBinding("areaRiservataServiceBinding");

            if (this._config.Parametri.UrlAreaRiservataService.StartsWith("https"))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
            }

            var ws = new AreaRiservataServiceSoapClient(binding, endPoint);

            var token = this._tokenApplicazioneService.GetToken(aliasComune);

            return new ServiceInstance<AreaRiservataServiceSoapClient>(ws, token);
        }
    }
}
