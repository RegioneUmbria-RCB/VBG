using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsUrlAccessoConsoleService;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVbgConsole
{
    public class UrlConsoleRepository : ServiceCreatorBase<WsUrlAccessoConsoleServiceClient>, IUrlConsoleRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(UrlConsoleRepository));
        private readonly ISoftwareResolver _softwareResolver;
        public UrlConsoleRepository(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory, ISoftwareResolver softwareResolver) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
            this._softwareResolver = softwareResolver;
        }

        public ConfigurazioneUrlConsole GetUrlAccesso()
        {
            return this.Call(ws =>
            {
                return ws.Service.GetUrlAccessoConsole(ws.Token, this._softwareResolver.Software);
            });
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override WsUrlAccessoConsoleServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsUrlAccessoConsoleServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlWsUrlAccessoConsoleService;
        }
    }
}