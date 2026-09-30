using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriLivornoConfigurazioneDrupalBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLivornoConfigurazioneDrupal>
    {
        public ParametriLivornoConfigurazioneDrupalBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            :base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriLivornoConfigurazioneDrupal Build()
        {
            var cfg = GetConfig();



            return new ParametriLivornoConfigurazioneDrupal(cfg.ServiziCittadino.UrlWsModulisticaDrupal);
        }
    }
}
