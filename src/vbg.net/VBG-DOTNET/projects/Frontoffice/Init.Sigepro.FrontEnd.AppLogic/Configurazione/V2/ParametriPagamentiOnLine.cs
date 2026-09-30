using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriPagamentiOnLine : IParametriConfigurazione
    {
        public readonly bool PermettiAnnullamentoPagamentiModello3;

        public ParametriPagamentiOnLine(bool permettiAnnullamentoPagamentiModello3)
        {
            this.PermettiAnnullamentoPagamentiModello3 = permettiAnnullamentoPagamentiModello3;
        }
    }


    public class ParametriPagamentiOnLineBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriPagamentiOnLine>
    {

        public ParametriPagamentiOnLineBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }

        public ParametriPagamentiOnLine Build()
        {
            var cfg = this.GetConfig();

            return new ParametriPagamentiOnLine(cfg.PagamentiPermettiAnnullamentoModello3);
        }
    }
}
