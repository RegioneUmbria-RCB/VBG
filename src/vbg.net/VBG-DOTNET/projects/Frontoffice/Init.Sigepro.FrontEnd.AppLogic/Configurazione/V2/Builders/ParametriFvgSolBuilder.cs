using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;

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
            var config = this.GetConfig();
            var parametri = config.ParametriFvgSol;

            if (parametri == null)
            {
                return ParametriFvgSol.NonAttiva;
            }

            return new ParametriFvgSol(parametri.WebServiceUrl, parametri.WebServiceUsername, parametri.WebServicePassword);
        }
    }
}
