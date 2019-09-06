using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriDimensioneAllegatiLiberiBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriDimensioneAllegatiLiberi>
    {
        public ParametriDimensioneAllegatiLiberiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            :base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriDimensioneAllegatiLiberi Build()
        {
            var cfg = GetConfig();

            return new ParametriDimensioneAllegatiLiberi(cfg.FormatiAllegatiLiberi??new AreaRiservataService.FormatoAllegatoLiberoDto[0]);
        }
    }
}
