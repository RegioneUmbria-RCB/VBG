using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriDatiCatastaliBuilder : IConfigurazioneBuilder<ParametriDatiCatastali>
    {
        private static class Constants
        {
            public const string ConfigKeyName = "MostraDatiCatastaliEstesi";
        }

        private readonly bool _mostraDatiCatastaliEstesi = false;

        public ParametriDatiCatastaliBuilder(IAppConfigurationReader appConfigurationReader)
        {
            var cfgKey = appConfigurationReader.GetSetting(Constants.ConfigKeyName);

            if (!String.IsNullOrEmpty(cfgKey))
                this._mostraDatiCatastaliEstesi = bool.Parse(cfgKey);
        }

        #region IBuilder<ParametriDatiCatastali> Members

        public ParametriDatiCatastali Build()
        {
            return new ParametriDatiCatastali(this._mostraDatiCatastaliEstesi);
        }

        #endregion
    }
}
