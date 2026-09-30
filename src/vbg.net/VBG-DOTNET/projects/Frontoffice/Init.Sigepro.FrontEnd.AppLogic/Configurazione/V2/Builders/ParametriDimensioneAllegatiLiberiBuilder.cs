using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.SIGePro.Manager.DTO.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriDimensioneAllegatiLiberiBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriDimensioneAllegatiLiberi>
    {
        public ParametriDimensioneAllegatiLiberiBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {

        }

        public ParametriDimensioneAllegatiLiberi Build()
        {
            var cfg = this.GetConfig();

            return new ParametriDimensioneAllegatiLiberi(cfg.FormatiAllegatiLiberi ?? new FormatoAllegatoLiberoDto[0]);
        }
    }
}
