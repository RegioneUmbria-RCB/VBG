//using Init.Sigepro.FrontEnd.AppLogic.Common;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

//namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
//{
//    public class ParametriGoogleMapsBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriGoogleMaps>
//    {
//        public ParametriGoogleMapsBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
//        {
//        }

//        public ParametriGoogleMaps Build()
//        {
//            var config = this.GetConfig();
//            var apiKey = config.GoogleMaps?.ApiKey ?? "";
//            var mapBounds = config.GoogleMaps?.MapBounds ?? "";

//            return new ParametriGoogleMaps(apiKey, mapBounds);
//        }
//    }
//}
