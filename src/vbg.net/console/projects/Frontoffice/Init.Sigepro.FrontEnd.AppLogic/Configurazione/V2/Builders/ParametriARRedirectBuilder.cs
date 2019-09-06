using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriARRedirectBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriARRedirect>
    {

        public ParametriARRedirectBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repository): 
            base(aliasResolver,repository)
        {

        }

        public ParametriARRedirect Build()
        {
            var config = GetConfig();


            return new ParametriARRedirect(config.AreaRiservataRedirect.VerticalizzazioneAttiva, config.AreaRiservataRedirect.NomeFile, config.AreaRiservataRedirect.UrlRedirect);
        }
    }
}
