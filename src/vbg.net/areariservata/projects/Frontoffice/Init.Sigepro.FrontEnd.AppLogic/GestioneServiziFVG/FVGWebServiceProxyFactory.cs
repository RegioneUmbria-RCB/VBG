using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGWebServiceProxyFactory
    {
        IConfigurazione<ParametriFvgSol> _config;

        public FVGWebServiceProxyFactory(IConfigurazione<ParametriFvgSol> config)
        {
            this._config = config;
        }

        public IFVGWebServiceProxy CreateService()
        {
            if (!string.IsNullOrEmpty(ConfigurationManager.AppSettings["FvgDatabasePersistenceMediumFactory.debugMode"]))
            {
                return new FVGFileSystemWebServiceProxy();
            }

            return new FVGWebServiceProxy(this._config);
        }
    }
}
