using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;

namespace VBG.AppLogic.SSU.Configurazione
{
    public class ParametriSsuBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriSsu>
    {
        private static class Constants
        {
            public const string SSU_API_BASEURL = "SSU_API_BASEURL";
        }


        public ParametriSsuBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository, IAppConfigurationReader appConfigurationReader)
            : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
        }


        #region IBuilder<ParametriSigeproSecurity> Members
        public ParametriSsu Build()
        {
            var config = this.GetConfig();
            var attiva = config.AreaRiservataSsu.Attiva;
            var baseUrlSsu = config.AreaRiservataSsu.BaseUrlApiCatalogoServizi;
            var idNodoDestinatario = config.AreaRiservataSsu.IdNodoDestinatario;
            var idEnteDestinatario = config.AreaRiservataSsu.IdEnteDestinatario;
            var idSportelloDestinatario = config.AreaRiservataSsu.IdSportelloDestinatario;
            var baseUrlValidator = config.AreaRiservataSsu.BaseUrlApiValidator;

            return new ParametriSsu(attiva, baseUrlSsu, idNodoDestinatario, idEnteDestinatario, idSportelloDestinatario, baseUrlValidator);
        }
        #endregion
    }
}
