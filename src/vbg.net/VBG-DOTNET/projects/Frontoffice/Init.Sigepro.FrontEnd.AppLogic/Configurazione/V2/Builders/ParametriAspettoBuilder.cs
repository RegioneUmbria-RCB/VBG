using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    internal class ParametriAspettoBuilder : AreaRiservataWsConfigBuilder, IConfigurazioneBuilder<ParametriAspetto>
    {
        private static class Constants
        {
            public const string ConfigKeyName = "file-configurazione-contenuti";
        }

        private string _nomeFileContenuti = String.Empty;

        public ParametriAspettoBuilder(IAliasSoftwareResolver aliasResolver, IConfigurazioneAreaRiservataRepository repo, IAppConfigurationReader appConfigurationReader)
            : base(aliasResolver, repo)
        {
            this._nomeFileContenuti = appConfigurationReader.GetSetting(Constants.ConfigKeyName);
        }

        #region IBuilder<ParametriAspetto> Members

        public ParametriAspetto Build()
        {
            var cfg = this.GetConfig();

            if (!String.IsNullOrEmpty(cfg.NomeConfigurazioneContenuti))
                this._nomeFileContenuti = cfg.NomeConfigurazioneContenuti;

            var intestazioneCertificatoDiInvio = cfg.IntestazioneCertificatoInvio;

            return new ParametriAspetto(this._nomeFileContenuti, intestazioneCertificatoDiInvio);
        }

        #endregion
    }
}
