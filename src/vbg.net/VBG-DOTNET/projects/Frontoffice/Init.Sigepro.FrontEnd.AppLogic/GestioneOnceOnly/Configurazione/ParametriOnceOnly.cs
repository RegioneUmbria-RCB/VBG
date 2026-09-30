using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione
{

    public class ParametriOnceOnlyBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriOnceOnly>
    {
        private static class Constants
        {
            public const string WSHOSTURL_ONCEONLY_MODELLI = "WSHOSTURL_ONCEONLY_MODELLI";
        }

        private readonly CacheParametriSigeproSecurity _cacheParametriSigeproSecurity;

        public ParametriOnceOnlyBuilder(CacheParametriSigeproSecurity cacheParametriSigeproSecurity, IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository configurazioneAreaRiservataRepository) : base(aliasResolver, configurazioneAreaRiservataRepository)
        {
            this._cacheParametriSigeproSecurity = cacheParametriSigeproSecurity;
        }

        public ParametriOnceOnly Build()
        {
            var cfg = this.GetConfig();

            bool attivaCompilazioneOnceOnly = cfg.AttivaCompilazioneOnceOnly;
            var onceOnlyBaseUrl = attivaCompilazioneOnceOnly ? this._cacheParametriSigeproSecurity.GetValoreCache(Constants.WSHOSTURL_ONCEONLY_MODELLI) : "";

            return new ParametriOnceOnly(attivaCompilazioneOnceOnly, onceOnlyBaseUrl);
        }
    }


    public class ParametriOnceOnly : IParametriConfigurazione
    {


        public bool AttivaCompilazioneOnceOnly { get; }

        public string OnceOnlyBaseUrl { get; } = "";

        public ParametriOnceOnly(bool attivaCompilazioneOnceOnly, string onceOnlyBaseUrl)
        {
            this.AttivaCompilazioneOnceOnly = attivaCompilazioneOnceOnly && !string.IsNullOrEmpty(onceOnlyBaseUrl);

            if (this.AttivaCompilazioneOnceOnly)
            {
                this.OnceOnlyBaseUrl = onceOnlyBaseUrl;
            }
        }
    }
}
