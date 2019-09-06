using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public static class ASParametriInvioStcExtensions
    {
        public static ParametriStcAidaSmart ToConfig(this ASParametriInvioStc p)
        {
            return new ParametriStcAidaSmart(
                p.UrlStc,
                p.Username,
                p.Password,
                new ParametriStcAidaSmart.RiferimentiSportello
                {
                    IdNodo = p.SportelloMittente.IdNodo,
                    IdEnte = p.SportelloMittente.IdEnte,
                    IdSportello = p.SportelloMittente.IdSportello
                },
                new ParametriStcAidaSmart.RiferimentiSportello
                {
                    IdNodo = p.SportelloDestinatario.IdNodo,
                    IdEnte = p.SportelloDestinatario.IdEnte,
                    IdSportello = p.SportelloDestinatario.IdSportello
                },
                p.UrlVisuraIstanza);
        }
    }


    internal class ParametriStcAidaSmartBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriStcAidaSmart>
    {

        public ParametriStcAidaSmartBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository arRepo)
            :base(aliasResolver, arRepo)
        {

        }

        public ParametriStcAidaSmart Build()
        {
            var cfg = GetConfig();

            return cfg.ParametriInvioStc.ToConfig();
        }
    }
}
