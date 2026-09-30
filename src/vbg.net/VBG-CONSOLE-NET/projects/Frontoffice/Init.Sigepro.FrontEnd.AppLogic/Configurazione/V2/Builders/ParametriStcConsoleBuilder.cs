using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public static class ConsoleParametriInvioStcExtensions
    {
        public static ParametriStcConsole ToConfig(this ASParametriInvioStc p)
        {
            return new ParametriStcConsole(
                p.UrlStc,
                p.Username,
                p.Password,
                new ParametriStcConsole.RiferimentiSportello
                {
                    IdNodo = p.SportelloMittente.IdNodo,
                    IdEnte = p.SportelloMittente.IdEnte,
                    IdSportello = p.SportelloMittente.IdSportello
                },
                new ParametriStcConsole.RiferimentiSportello
                {
                    IdNodo = p.SportelloDestinatario.IdNodo,
                    IdEnte = p.SportelloDestinatario.IdEnte,
                    IdSportello = p.SportelloDestinatario.IdSportello
                });
        }
    }


    internal class ParametriStcConsoleBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriStcConsole>
    {

        public ParametriStcConsoleBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository arRepo)
            : base(aliasResolver, arRepo)
        {

        }

        public ParametriStcConsole Build()
        {
            var cfg = this.GetConfig();

            return cfg.ParametriInvioStc.ToConfig();
        }
    }
}
