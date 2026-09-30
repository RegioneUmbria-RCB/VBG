using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    internal class VisuraIstanzeServiceCreator : IVisuraIstanzeServiceCreator
    {
        private readonly IConfigurazione<ParametriStcConsole> _config;
        private readonly IConfigurazione<ParametriServiziAreaRiservata> _configServiziAR;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;

        public VisuraIstanzeServiceCreator(IConfigurazione<ParametriStcConsole> config, IConfigurazione<ParametriServiziAreaRiservata> configServiziAR, ITokenApplicazioneService tokenApplicazioneService)
        {
            this._config = config ?? throw new System.ArgumentNullException(nameof(config));
            this._configServiziAR = configServiziAR ?? throw new System.ArgumentNullException(nameof(configServiziAR));
            this._tokenApplicazioneService = tokenApplicazioneService ?? throw new System.ArgumentNullException(nameof(tokenApplicazioneService));
        }

        public ServiceInstance<IstanzeWsSoapClient> CreateClient()
        {
            var wsUrl = this._configServiziAR.Parametri.UrlVisuraIstanzaConsole;
            var aliasDestinazione = this._config.Parametri.SportelloDestinatario.IdEnte;

            var endPoint = new EndpointAddress(wsUrl);

            var binding = new BasicHttpBinding("istanzeVisuraServiceBinding");

            if (wsUrl.StartsWith("https"))
            {
                binding.Security.Mode = BasicHttpSecurityMode.Transport;
                binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.None;
            }

            var ws = new IstanzeWsSoapClient(binding, endPoint);
            var token = this._tokenApplicazioneService.GetToken(aliasDestinazione);

            return new ServiceInstance<IstanzeWsSoapClient>(ws, token);
        }
    }
}
