using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriLoghiBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriLoghi>
    {
        public ParametriLoghiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            :base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }


        public ParametriLoghi Build()
        {
            var cfg = GetConfig();

            return new ParametriLoghi(cfg.ConfigurazioneLoghi.CodiceOggettoLogoComune, cfg.ConfigurazioneLoghi.CodiceOggettoLogoRegione, cfg.ConfigurazioneLoghi.UrlLogo);
        }
    }
}
