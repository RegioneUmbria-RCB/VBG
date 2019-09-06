using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriFvgSolBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriFvgSol>
    {
        public ParametriFvgSolBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazioneAreaRiservataRepository repo)
            : base(aliasSoftwareResolver, repo)
        {

        }

        public ParametriFvgSol Build()
        {
            var config = GetConfig();
            var parametri = config.ParametriFvgSol;

            if (parametri == null)
            {
                return ParametriFvgSol.NonAttiva;
            }

            return new ParametriFvgSol(parametri.WebServiceUrl, parametri.WebServiceUsername, parametri.WebServicePassword);
        }
    }
}
