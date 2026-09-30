using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration;
using VBG.Shared.Infrastructure.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGWebServiceProxyFactory
    {
        private readonly IConfigurazione<ParametriFvgSol> _config;
        private readonly IFVGDebugConfiguration _debugConfiguration;
        private readonly IBindingFactory _bindingFactory;

        public FVGWebServiceProxyFactory(IConfigurazione<ParametriFvgSol> config, IFVGDebugConfiguration debugConfiguration, IBindingFactory bindingFactory)
        {
            this._config = config;
            this._debugConfiguration = debugConfiguration;
            this._bindingFactory = bindingFactory;
        }

        public IFVGWebServiceProxy CreateService()
        {
            if (this._debugConfiguration.IsDebugEnabled)
            {
                return new FVGFileSystemWebServiceProxy();
            }

            return new FVGWebServiceProxy(this._config, this._bindingFactory);
        }
    }
}
