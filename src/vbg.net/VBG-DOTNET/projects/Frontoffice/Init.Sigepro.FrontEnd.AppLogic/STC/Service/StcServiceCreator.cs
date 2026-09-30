// -----------------------------------------------------------------------
// <copyright file="StcServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Stc;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Service
{
    internal class StcServiceCreator : IStcServiceCreator
    {
        private readonly IConfigurazione<ParametriStc> _configurazione;
        private readonly IBindingFactory _bindingFactory;
        private readonly StcToken _stcToken;
        public StcServiceCreator(IConfigurazione<ParametriStc> configurazione, IBindingFactory bindingFactory)
        {
            this._configurazione = configurazione;
            this._bindingFactory = bindingFactory;
            this._stcToken = new StcToken(this._configurazione, bindingFactory);
        }

        public ParametriStc ConfigurazioneStc
        {
            get
            {
                return this._configurazione.Parametri;
            }
        }

        public ServiceInstance<StcClient> CreateClient()
        {
            var endPoint = new EndpointAddress(this._configurazione.Parametri.UrlInvio);
            var binding = this._bindingFactory.CreateAndConfigure("stcServiceBinding");
            var ws = new StcClient(binding, endPoint);
            var token = this._stcToken.GetToken();
            return new ServiceInstance<StcClient>(ws, token);
        }
    }
}