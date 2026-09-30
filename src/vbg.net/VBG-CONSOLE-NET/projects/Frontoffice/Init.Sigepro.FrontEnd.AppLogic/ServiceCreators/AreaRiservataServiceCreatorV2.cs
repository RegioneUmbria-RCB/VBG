using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ServiceCreators
{
    internal class AreaRiservataServiceCreatorV2
    {
        IConfigurazione<ParametriSigeproSecurity> _config;
        ITokenApplicazioneService _tokenApplicazioneService;
        IAliasResolver _aliasResolver;
        ILog _log = LogManager.GetLogger(typeof(AreaRiservataServiceCreator));

        public AreaRiservataServiceCreatorV2(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver)
        {
            Condition.Requires(config, "config").IsNotNull();
            Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();
            Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();

            this._config = config;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
        }

        public ServiceInstance<AreaRiservataServiceSoapClient> CreateClient()
        {
            _log.DebugFormat("Inizializzazione del web service di gestione dati dell'area riservata (V2) all'endpoint {0} utilizzando il binding OggettiServiceBinding", this._config.Parametri.UrlAreaRiservataService);

            var endPoint = new EndpointAddress(this._config.Parametri.UrlAreaRiservataService);

            var binding = new BasicHttpBinding("areaRiservataServiceBinding");
            var ws = new AreaRiservataServiceSoapClient(binding, endPoint);

            var token = this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);

            return new ServiceInstance<AreaRiservataServiceSoapClient>(ws, token);
        }
    }
}
