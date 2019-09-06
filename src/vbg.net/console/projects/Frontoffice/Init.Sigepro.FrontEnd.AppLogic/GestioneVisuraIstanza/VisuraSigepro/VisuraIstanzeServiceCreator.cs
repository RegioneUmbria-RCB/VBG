using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    internal class VisuraIstanzeServiceCreator: IVisuraIstanzeServiceCreator
    {
        IConfigurazione<ParametriStcAidaSmart> _config;
        ITokenApplicazioneService _tokenApplicazioneService;
        IAliasResolver _aliasResolver;

        public VisuraIstanzeServiceCreator(IConfigurazione<ParametriStcAidaSmart> config, ITokenApplicazioneService tokenApplicazioneService, IAliasResolver aliasResolver)
        {
            Condition.Requires(config, "config").IsNotNull();
            Condition.Requires(tokenApplicazioneService, "tokenApplicazioneService").IsNotNull();

            this._config = config;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._aliasResolver = aliasResolver;
        }

        public ServiceInstance<IstanzeWsSoapClient> CreateClient()
        {
            return this.CreateClient(this._aliasResolver.AliasComune);
        }


        private ServiceInstance<IstanzeWsSoapClient> CreateClient(string aliasComune)
        {
            var wsUrl = this._config.Parametri.UrlVisuraIstanze;
            var aliasDestinazione = _config.Parametri.SportelloDestinatario.IdEnte;

            var endPoint = new EndpointAddress(wsUrl);

            var binding = new BasicHttpBinding("istanzeVisuraServiceBinding");

            var ws = new IstanzeWsSoapClient(binding, endPoint);
            var token = this._tokenApplicazioneService.GetToken(aliasDestinazione);

            return new ServiceInstance<IstanzeWsSoapClient>(ws, token);
        }


    }
}
